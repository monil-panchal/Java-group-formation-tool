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



    //  @Mock
//  QuestionDAOImpl questionDAO;
//
//  @InjectMocks
//  QuestionService questionService = QuestionServiceImpl.getInstance();
    QuestionService questionService;
    UserService userService;
    SurveyQuestionsServiceImpl surveyQuestionsService;
    SurveyAlgorithmService surveyAlgorithmService;
    private Logger logger = LoggerFactory.getLogger(SurveyAlgorithmServiceTest.class);
    int responseVectorLength;

    @BeforeEach
    public void init() {
        questionService = QuestionServiceImpl.getInstance();
        surveyQuestionsService = SurveyQuestionsServiceImpl.getInstance();
        userService = UserServiceImpl.getInstance();
        surveyAlgorithmService = SurveyAlgorithmService.getInstance();
        responseVectorLength = surveyAlgorithmService.getVectorLength(34L).get();
    }

    @Test
    void testTextToVector() {
        double[] ints = surveyAlgorithmService.convertFreeTextToVector("Hello World!");
        Assertions.assertEquals(ints[0], 708854109);
        Assertions.assertEquals(ints[1], 925923709);
        Assertions.assertEquals(ints[2], 0);
    }
}