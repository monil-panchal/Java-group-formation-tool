package com.assessme.service;

import com.assessme.db.dao.SurveyQuestionsDAOImpl;
import com.assessme.model.Survey;
import com.assessme.model.SurveyQuestionsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-15
 */
@ExtendWith(MockitoExtension.class)
public class SurveyQuestionsServiceTest {

    private final Logger logger = LoggerFactory.getLogger(SurveyQuestionsServiceTest.class);

    @Mock
    private SurveyQuestionsDAOImpl surveyQuestionsDAO;

    @Mock
    private SurveyQuestionsServiceImpl surveyQuestionsService;

    @Mock
    private SurveyServiceImpl surveyService;

    private Optional<Survey> surveyFromDB;

    private Optional<SurveyQuestionsDTO> optionalSurveyQuestionsDTO;

    @Test
    public void addQuestionsToSurvey() throws Exception {

        logger.info("Running unit test for adding questions to the survey");

        SurveyQuestionsDTO surveyQuestionsDTO = new SurveyQuestionsDTO();
        surveyQuestionsDTO.setSurveyId(1L);
        surveyQuestionsDTO.setQuestionList(List.of(1L, 2L, 3L));

        Optional<SurveyQuestionsDTO> optionalSurveyQuestionsDTO2 = Optional.of(surveyQuestionsDTO);

        Survey survey = new Survey();
        survey.setSurveyId(1L);
        survey.setSurveyName("Test survey");
        survey.setDescription("Sample test survey");
        survey.setCourseId(1L);
        survey.setUserId(1L);

        Optional<Survey> optionalSurvey = Optional.of(survey);

        Mockito.when(surveyService.getSurvey(1L)).thenReturn(optionalSurvey);
        surveyFromDB = surveyService.getSurvey(1L);

        Mockito.when(surveyQuestionsService.addQuestionsToSurvey(surveyQuestionsDTO)).thenReturn(optionalSurveyQuestionsDTO2);
        optionalSurveyQuestionsDTO = surveyQuestionsService.addQuestionsToSurvey(surveyQuestionsDTO);

        Assert.isTrue(optionalSurveyQuestionsDTO.isPresent(), "Survey should not be empty");
        Assert.notEmpty(optionalSurveyQuestionsDTO.get().getQuestionList(), "question list should not null");
        Assert.notNull(optionalSurveyQuestionsDTO.get().getSurveyId(), "Survey id should not be null");

    }


    @Test
    void getSurveysForCourse() throws Exception {

        logger.info("Running unit test for getting questions of the survey");

        SurveyQuestionsDTO surveyQuestionsDTO = new SurveyQuestionsDTO();
        surveyQuestionsDTO.setSurveyId(1L);
        surveyQuestionsDTO.setQuestionList(List.of(1L, 2L, 3L));

        Survey survey = new Survey();
        survey.setSurveyId(1L);
        survey.setSurveyName("Test survey");
        survey.setDescription("Sample test survey");
        survey.setCourseId(1L);
        survey.setUserId(1L);

        Optional<SurveyQuestionsDTO> optionalSurveyQuestionsDTO2 = Optional.of(surveyQuestionsDTO);

        Mockito.when(surveyQuestionsService.getSurveyQuestions(1L)).thenReturn(optionalSurveyQuestionsDTO2);
        optionalSurveyQuestionsDTO = surveyQuestionsService.getSurveyQuestions(1L);

        Assert.notEmpty(optionalSurveyQuestionsDTO.get().getQuestionList(), "question list is not null");

    }
}
