package com.assessme.service;

import com.assessme.model.Question;
import com.assessme.model.SurveyQuestionResponseData;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.model.SurveyResponseDTO.UserResponse;
import com.assessme.util.AppConstant;
import com.google.common.base.Preconditions;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Darshan Kathiriya
 * @created 15-July-2020 4:02 PM
 */
public class SurveyAlgorithmService {

    final static HashFunction hf = Hashing.md5();
    private static SurveyAlgorithmService instance;

    final int GROUP_SIZE = 2;
    private final Logger logger = LoggerFactory.getLogger(SurveyAlgorithmService.class);
    QuestionService questionService;
    SurveyQuestionsServiceImpl surveyQuestionsService;
    SurveyResponseService surveyResponseService;

    public SurveyAlgorithmService() {
        questionService = QuestionServiceImpl.getInstance();
        surveyQuestionsService = SurveyQuestionsServiceImpl.getInstance();
        surveyResponseService = SurveyResponseServiceImpl.getInstance();
    }

    public static SurveyAlgorithmService getInstance() {
        if (instance == null) {
            instance = new SurveyAlgorithmService();
        }
        return instance;
    }

    public HashMap<Integer, List<Long>> formGroupsForSurvey(long surveyId) throws Exception {
        int vectorLength = getVectorLength(surveyId).get();
        logger.info(String.format("Vector Length: %d", vectorLength));
        HashMap<Integer, List<Long>> groupsIds = createGroups(surveyId, vectorLength);
        logger.info(String.format("created groups: %d", groupsIds.size()));
        return groupsIds;
    }

    Optional<Integer> getVectorLength(long surveyId) {
        try {
            int vecLength = 0;
            Optional<SurveyQuestionsDTO> surveyQuestions = surveyQuestionsService
                .getSurveyQuestions(surveyId);
            List<Long> questionList = surveyQuestions.get().getQuestionList();
            for (long questionId : questionList) {
                logger.info(String.format("%d", questionId));
                Question questionById = questionService.getQuestionById(questionId).get();
                switch (questionById.getQuestionTypeId()) {
                    case 1:
                        vecLength += 1;
                        break;
                    case 2:
                    case 3:
                        int[] optionValues = questionById.getOptionValue();
                        vecLength += optionValues.length;
                        break;
                    case 4:
                        vecLength += 1;
                    default:
                        logger.error(String.format("question type: %d found for question: %d",
                            questionById.getQuestionTypeId(), questionById.getQuestionId()));
                        break;
                }
            }
            return Optional.of(vecLength);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    List<Long> formAGroup(double[] vec, HashMap<Long, double[]> responseVector) {
        List<Long> group = new ArrayList<>();
        int members = GROUP_SIZE - 1;
        while (members > 0) {
            long userId = 0;
            double similarity = Double.MIN_VALUE;
            for (Map.Entry<Long, double[]> entry : responseVector.entrySet()) {
                long key = entry.getKey();
                double[] otherVec = entry.getValue();
                double new_similarity = cosineSimilarity(vec, otherVec);
                if (new_similarity > similarity) {
                    similarity = new_similarity;
                    userId = key;
                }
            }
            responseVector.remove(userId);
            group.add(userId);
            members--;
        }
        return group;
    }

    HashMap<Integer, List<Long>> createGroups(long surveyId, int vectorLength) throws Exception {
        HashMap<Long, double[]> responseVectors = getResponseVectors(surveyId, vectorLength).get();
        HashMap<Integer, List<Long>> groups = new HashMap<>();
        try {
            logger.info(
                String.format("Respnse Vector with Size: %d created", responseVectors.size()));
            int groupNumber = 1;
            while (responseVectors.size() / GROUP_SIZE >= 1) {
                int random = ThreadLocalRandom.current().nextInt(0, responseVectors.size());
                Long[] keys = responseVectors.keySet().toArray(new Long[responseVectors.size()]);
                long userId = keys[random];
                double[] userResponseVec = responseVectors.remove(userId);
                List<Long> group = formAGroup(userResponseVec, responseVectors);
                group.add(userId);
                //TODO: check here if userIds are deleted?
                logger.info(String.format("Responses are available: %d", responseVectors.size()));
                group.stream().forEach((i) -> responseVectors.remove(i));
                groups.put(groupNumber, group);
                groupNumber++;
            }
            if (responseVectors.size() != 0) {
                groups.put(groupNumber,
                    responseVectors.keySet().stream().collect(Collectors.toList()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return groups;
    }


    Optional<HashMap<Long, double[]>> getResponseVectors(long surveyId, int vectorLength)
        throws Exception {
        try {
            List<UserResponse> userResponses = surveyResponseService
                .getSurveyQuestionsForStudent(surveyId)
                .getUsers();

            HashMap<Long, double[]> responseList = new HashMap<>();
            for (UserResponse u : userResponses) {
                double[] vec = new double[vectorLength];
                int vecIndex = 0;
                //TODO: replace with get response list by survey and user.
                //TODO: get question by user and survey
                for (SurveyQuestionResponseData q : u.getQuestions()) {
                    logger.info(String.format("Vector Index: %d", vecIndex));
                    List<Integer> optionValues;
                    logger.info(String.format("Question Type: %d", q.getQuestionTypeId()));
                    switch (q.getQuestionTypeId().intValue()) {
                        case 1:
                            vec[vecIndex++] = Integer.parseInt(q.getData());
                            break;
                        case 2:
                        case 3:

                            List<Integer> questionOptions = Arrays
                                .stream(questionService.getQuestionById(q.getQuestionId()).get()
                                    .getOptionValue()).boxed().collect(Collectors.toList());
                            q.getOptionValue().forEach(System.out::println);
                            for (int opValue : q.getOptionValue()) {
                                vec[vecIndex + questionOptions.indexOf(opValue)] = opValue;
                                vecIndex++;
                            }
                            break;
                        case 4:
                            double string_dist = Math.abs(
                                cosineSimilarity(convertFreeTextToVector(q.getData()),
                                    getNewVec()));
                            vec[vecIndex++] = string_dist;
                            break;
                        default:
                            break;
                    }
                }
                responseList.put(u.getUserId(), vec);
            }
            return Optional.of(responseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    double[] convertFreeTextToVector(String responseString) {
        responseString = responseString.replaceAll("[^a-zA-Z0-9]", " ");
        String[] split = responseString.split("\\s+");
        double[] vec = new double[AppConstant.STRING_VEC_LENGTH];
        int idx = 0;
        for (String i : split) {
            vec[idx] = hf.hashString(i.toLowerCase(), Charset.defaultCharset()).asInt();
            idx += 1;
        }
        return vec;
    }

    public double[] getNewVec() {
        double[] vec = new double[AppConstant.STRING_VEC_LENGTH];
        Arrays.fill(vec, 1);
        return vec;
    }

    public double cosineSimilarity(double[] vectorA, double[] vectorB) {
        Preconditions.checkState(vectorA.length == vectorB.length,
            "Need both vector of same length");
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
