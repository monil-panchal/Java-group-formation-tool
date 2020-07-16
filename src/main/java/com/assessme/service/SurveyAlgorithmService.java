package com.assessme.service;

import com.assessme.model.Question;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.model.User;
import com.assessme.util.AppConstant;
import com.google.common.base.Preconditions;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Darshan Kathiriya
 * @created 15-July-2020 4:02 PM
 */
public class SurveyAlgorithmService {
    final static HashFunction hf = Hashing.md5();
    private static SurveyAlgorithmService instance;
    final int GROUP_SIZE = 4;
    long surveyId = 24;
    QuestionService questionService;
    UserService userService;
    SurveyQuestionsServiceImpl surveyQuestionsService;
    private Logger logger = LoggerFactory.getLogger(SurveyAlgorithmService.class);

    public SurveyAlgorithmService() {
        questionService = QuestionServiceImpl.getInstance();
        surveyQuestionsService = SurveyQuestionsServiceImpl.getInstance();
    }

    public static SurveyAlgorithmService getInstance() {
        if (instance == null) {
            instance = new SurveyAlgorithmService();
        }
        return instance;
    }

    public HashMap<Integer, List<User>> formGroupsForSurvey(long surveyId) throws Exception {
        int vectorLength = getVectorLength(surveyId).get();

        logger.info(String.format("Vector Length: %d", vectorLength));
        HashMap<Integer, List<User>> groups = new HashMap<>();
        List<User> group1 = new ArrayList<>();
        List<User> group2 = new ArrayList<>();
        group1.add(new User("B001", "Dash_1", "", "", "", true));
        group1.add(new User("B002", "Dash_2", "", "", "", true));
        group2.add(new User("B003", "Dash_3", "", "", "", true));
        groups.put(1, group1);
        groups.put(2, group2);
        return groups;
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

    void createGroups() {
        HashMap<Long, double[]> responseVectors = getResponseVectors().get();
        HashMap<Integer, List<Long>> groups = new HashMap<>();
        int groupNumber = 1;
        while (responseVectors.size() / GROUP_SIZE >= 1) {
            int random = ThreadLocalRandom.current().nextInt(0, responseVectors.size());
            Long[] keys = (Long[]) responseVectors.keySet().toArray();
            long userId = keys[random];
            double[] userResponseVec = responseVectors.remove(userId);
            List<Long> group = formAGroup(userResponseVec, responseVectors);
            group.add(userId);
            //TODO: check here if userIds are deleted?
            group.stream().forEach((i) -> responseVectors.remove(i));
            groups.put(groupNumber, group);
            groupNumber++;
        }
        groups.put(groupNumber, responseVectors.keySet().stream().collect(Collectors.toList()));
    }

    List<Long> formAGroup(double[] vec, HashMap<Long, double[]> responseVector) {
        List<Long> group = new ArrayList<>();
        int members = 3;
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

    Optional<HashMap<Long, double[]>> getResponseVectors() {
        try {
            // TODO: replace with get users list by survey.
            List<User> userList = userService.getUserList().get();
            HashMap<Long, double[]> responseList = new HashMap<>();
            for (User u : userList) {
//                double[] vec = getVectorForUser(u);
//                responseList.put(u.getUserId(), vec);
            }
            return Optional.of(responseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    double[] getVectorForUser(User u, int vectorLength) throws Exception {
        double[] vec = new double[vectorLength];
        int vecIndex = 0;
        //TODO: replace with get response list by survey and user.
//
//        List<SurveyResponse> responses = new ArrayList<>();
//        responses.add(new SurveyResponse(1L,"12"));
//        responses.add(new SurveyResponse(2L, "Hello World!"));
        //TODO: get question by user and survey
        Optional<List<Question>> questionsByUser = questionService.getQuestionsByUser(u);
        for (Question q : questionsByUser.get()) {
            int[] optionValues;
            switch (q.getQuestionTypeId()) {
                case 1:
                    //TODO: replace hardcoded value with actual response
                    int numResponseValue = 1;
                    vec[vecIndex++] = numResponseValue;
                    break;
                case 2:
                    //TODO: replace hardcoded value with actual response
                    int mcqResponseValue = 1;
                    optionValues = q.getOptionValue();
                    int optionIndex = Arrays.asList(optionValues).indexOf(mcqResponseValue);
                    vec[(++vecIndex) + optionIndex] = mcqResponseValue;
                    break;
                case 3:
                    optionValues = q.getOptionValue();
                    //TODO: replace hardcoded 1 with actual respones.
                    //Probably loop will be required.
                    vec[vecIndex++] = Arrays.asList(optionValues).indexOf(1);
                    break;
                case 4:
                    //TODO: Use response provided by user.
                    String responseString = "User Response will be used";
                    double string_dist = Math.abs(
                            cosineSimilarity(convertFreeTextToVector(responseString), getNewVec()));
                    vec[vecIndex] = string_dist;
//                    System.arraycopy(vecString, 0, vec, vecIndex, vecString.length);
                    vecIndex += 1;
                default:
                    break;
            }
        }
        return vec;
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
        double[] vec = new double[128];
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
