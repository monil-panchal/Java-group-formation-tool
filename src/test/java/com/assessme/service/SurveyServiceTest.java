package com.assessme.service;

import com.assessme.db.dao.SurveyDAOImpl;
import com.assessme.model.Survey;
import com.assessme.util.SurveyStatusConstant;
import org.junit.jupiter.api.Assertions;
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
 * Created on: 2020-07-14
 */
@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {

    private final Logger logger = LoggerFactory.getLogger(SurveyServiceTest.class);

    @Mock
    private SurveyDAOImpl surveyDAO;

    @Mock
    private SurveyServiceImpl surveyService;

    private Optional<Survey> surveyFromDB;

    @Test
    public void addSurveyTest() throws Exception {

        logger.info("Running unit test for creating the survey");

        Survey survey = new Survey();
        survey.setSurveyId(1L);
        survey.setSurveyName("Test survey");
        survey.setDescription("Sample test survey");
        survey.setCourseId(1L);
        survey.setUserId(1L);
        survey.setStatus(SurveyStatusConstant.PUBLISHED.getSurveyStatus());

        Optional<Survey> optionalSurvey = Optional.of(survey);

        Mockito.when(surveyService.addSurvey(survey)).thenReturn(optionalSurvey);
        surveyFromDB = surveyService.addSurvey(survey);

        Assert.isTrue(surveyFromDB.isPresent(), "Survey should not be empty");
        Assert.notNull(surveyFromDB.get().getSurveyId(), "Survey id should not be null");
        Assert.notNull(surveyFromDB.get().getCourseId(), "Survey course id should not be null");
        Assert.notNull(surveyFromDB.get().getUserId(), "Survey user id should not be null");
        Assertions.assertEquals(surveyFromDB.get().getUserId(), survey.getUserId());
        Assertions.assertEquals(surveyFromDB.get().getCourseId(), survey.getCourseId());
        Assertions.assertEquals(SurveyStatusConstant.PUBLISHED.getSurveyStatus(), surveyFromDB.get().getStatus());

    }

    @Test
    void getSurveysForCourse() throws Exception {

        logger.info("Running unit test for getting survey for the course");

        Survey survey = new Survey();
        survey.setSurveyId(1L);
        survey.setSurveyName("Test survey");
        survey.setDescription("Sample test survey");
        survey.setCourseId(1L);
        survey.setUserId(1L);

        List<Survey> surveyList = List.of(survey);

        Mockito.when(surveyService.getSurveysForCourse(1L)).thenReturn(surveyList);
        Assert.notEmpty(surveyService.getSurveysForCourse(1L), "survey list is not null");

    }

    @Test
    void getSurveyTest() throws Exception {

        logger.info("Running unit test for getting a survey");

        Survey survey = new Survey();
        survey.setSurveyId(1L);
        survey.setSurveyName("Test survey");
        survey.setDescription("Sample test survey");
        survey.setCourseId(1L);
        survey.setUserId(1L);

        Optional<Survey> optionalSurvey = Optional.of(survey);

        Mockito.when(surveyService.getSurvey(1L)).thenReturn(optionalSurvey);
        surveyFromDB = surveyService.getSurvey(1L);

        Assert.isTrue(surveyFromDB.isPresent(), "Survey should not be empty");
        Assert.notNull(surveyFromDB.get().getSurveyId(), "Survey id should not be null");
        Assert.notNull(surveyFromDB.get().getCourseId(), "Survey course id should not be null");
        Assert.notNull(surveyFromDB.get().getUserId(), "Survey user id should not be null");

    }

    @Test
    void updateSurveyStatus() throws Exception {

        logger.info("Running unit test for updating a survey");

        Survey survey = new Survey();
        survey.setSurveyId(1L);
        survey.setCourseId(1L);
        survey.setUserId(1L);
        survey.setStatus(SurveyStatusConstant.PUBLISHED.getSurveyStatus());

        Optional<Survey> optionalSurvey = Optional.of(survey);

        Mockito.when(surveyService.updateSurveyStatus(survey)).thenReturn(optionalSurvey);
        surveyFromDB = surveyService.updateSurveyStatus(survey);

        Assert.isTrue(surveyFromDB.isPresent(), "Survey should not be empty");
        Assert.notNull(surveyFromDB.get().getSurveyId(), "Survey id should not be null");
        Assert.notNull(surveyFromDB.get().getCourseId(), "Survey course id should not be null");
        Assert.notNull(surveyFromDB.get().getUserId(), "Survey user id should not be null");
        Assertions.assertEquals(SurveyStatusConstant.PUBLISHED.getSurveyStatus(), surveyFromDB.get().getStatus());

    }

}
