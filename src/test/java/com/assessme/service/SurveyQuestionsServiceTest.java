package com.assessme.service;

import com.assessme.db.dao.SurveyQuestionsDAOImpl;
import com.assessme.model.QuestionDetailsDTO;
import com.assessme.model.Survey;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.model.SurveyQuestionsDetails;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author: monil Created on: 2020-07-15
 */
@ExtendWith(MockitoExtension.class)
public class SurveyQuestionsServiceTest {

    private final Logger logger = LoggerFactory.getLogger(SurveyQuestionsServiceTest.class);

    @Mock
    private SurveyQuestionsDAOImpl surveyQuestionsDAO;

    @Mock
    private SurveyQuestionsServiceImpl surveyQuestionsService;

    @Mock
    private QuestionService questionService;

    @Mock
    private SurveyServiceImpl surveyService;

    private Optional<Survey> surveyFromDB;

    private Optional<SurveyQuestionsDTO> optionalSurveyQuestionsDTO;

    private Survey survey;

    @BeforeEach
    public void init() {
        survey = new Survey.Builder(1L)
            .addName("Test survey")
            .addDescription("Sample test survey")
            .forCourse(1L)
            .createdByUser(1L)
            .build();
    }

    @Test
    public void addQuestionsToSurveyTest() throws Exception {

        logger.info("Running unit test for adding questions to the survey");

        SurveyQuestionsDTO surveyQuestionsDTO = new SurveyQuestionsDTO();
        surveyQuestionsDTO.setSurveyId(1L);
        surveyQuestionsDTO.setQuestionList(List.of(1L, 2L, 3L));

        Optional<SurveyQuestionsDTO> optionalSurveyQuestionsDTO2 = Optional.of(surveyQuestionsDTO);
        Optional<Survey> optionalSurvey = Optional.of(survey);

        Mockito.when(surveyService.getSurvey(1L)).thenReturn(optionalSurvey);
        surveyFromDB = surveyService.getSurvey(1L);

        Mockito.when(surveyQuestionsService.addQuestionsToSurvey(surveyQuestionsDTO))
            .thenReturn(optionalSurveyQuestionsDTO2);
        optionalSurveyQuestionsDTO = surveyQuestionsService
            .addQuestionsToSurvey(surveyQuestionsDTO);

        Assert.isTrue(optionalSurveyQuestionsDTO.isPresent(), "Survey should not be empty");
        Assert.notEmpty(optionalSurveyQuestionsDTO.get().getQuestionList(),
            "question list should not null");
        Assert.notNull(optionalSurveyQuestionsDTO.get().getSurveyId(),
            "Survey id should not be null");

    }

    @Test
    public void getSurveyQuestionsTest() throws Exception {

        logger.info("Running unit test for getting questions of the survey");

        SurveyQuestionsDTO surveyQuestionsDTO = new SurveyQuestionsDTO();
        surveyQuestionsDTO.setSurveyId(1L);
        surveyQuestionsDTO.setQuestionList(List.of(1L, 2L, 3L));

        Optional<SurveyQuestionsDTO> optionalSurveyQuestionsDTO2 = Optional.of(surveyQuestionsDTO);

        Mockito.when(surveyQuestionsService.getSurveyQuestions(1L))
            .thenReturn(optionalSurveyQuestionsDTO2);
        optionalSurveyQuestionsDTO = surveyQuestionsService.getSurveyQuestions(1L);

        Assert.notEmpty(optionalSurveyQuestionsDTO.get().getQuestionList(),
            "question list is not null");
    }

    @Test
    public void getSurveyQuestionsDetailsTest() throws Exception {

        logger.info("Running unit test for getting questions with details for the survey");

        SurveyQuestionsDetails surveyQuestionsDetails = new SurveyQuestionsDetails();

        QuestionDetailsDTO questionDetailsDTO = new QuestionDetailsDTO.Builder(1L)
            .hasQuestionText("Sample question text")
            .hasQuestionTypeId(1)
            .hasQuestionTypeText("Numeric")
            .build();

        surveyQuestionsDetails.setSurveyId(1L);
        surveyQuestionsDetails.setQuestions(List.of(questionDetailsDTO));

        Optional<SurveyQuestionsDetails> optionalSurveyQuestionsDetails = Optional
            .of(surveyQuestionsDetails);

        Mockito.when(surveyQuestionsService.getSurveyQuestionsDetails(1L))
            .thenReturn(Optional.of(surveyQuestionsDetails));
        optionalSurveyQuestionsDetails = surveyQuestionsService.getSurveyQuestionsDetails(1L);

        Assert.notNull(optionalSurveyQuestionsDetails,
            "Survey question details retrieved from DB should not be null");
        Assert.notEmpty(optionalSurveyQuestionsDetails.get().getQuestions(),
            "Survey question list should not be empty");

    }
}
