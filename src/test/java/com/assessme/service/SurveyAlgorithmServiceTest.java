package com.assessme.service;

import com.assessme.model.Question;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.model.User;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Darshan Kathiriya
 * @created 15-July-2020 4:12 PM
 */
@ExtendWith(MockitoExtension.class)
class SurveyAlgorithmServiceTest {

    static HashFunction hf = Hashing.md5();
    final int GROUP_SIZE = 4;
    long surveyId = 24;
    //  @Mock
//  QuestionDAOImpl questionDAO;
//
//  @InjectMocks
//  QuestionService questionService = QuestionServiceImpl.getInstance();
    QuestionService questionService;
    UserService userService;
    SurveyQuestionsServiceImpl surveyQuestionsService;
    private Logger logger = LoggerFactory.getLogger(SurveyAlgorithmServiceTest.class);
    int responseVectorLength;

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

    @BeforeEach
    public void init() {
        questionService = QuestionServiceImpl.getInstance();
        surveyQuestionsService = SurveyQuestionsServiceImpl.getInstance();
        userService = UserServiceImpl.getInstance();
        responseVectorLength = getVectorLength().get();
    }

    Optional<Integer> getVectorLength() {
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
                        vecLength += AppConstant.STRING_VEC_LENGTH;
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

    @Test
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
        }
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

    @Test
    Optional<HashMap<Long, double[]>> getResponseVectors() {
        try {
            // TODO: replace with get users list by survey.
            List<User> userList = userService.getUserList().get();
            HashMap<Long, double[]> responseList = new HashMap<>();
            for (User u : userList) {
                double[] vec = getVectorForUser(u);
                responseList.put(u.getUserId(), vec);
            }
            return Optional.of(responseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    double[] getVectorForUser(User u) throws Exception {
        double[] vec = new double[responseVectorLength];
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

    @Test
    void testTextToVector() {
        double[] ints = convertFreeTextToVector("Hello World!");
        Assertions.assertEquals(ints[0], 708854109);
        Assertions.assertEquals(ints[1], 925923709);
        Assertions.assertEquals(ints[2], 0);
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

}