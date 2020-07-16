package com.assessme.model;

import com.assessme.util.SurveyStatusConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author: monil Created on: 2020-07-13
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
public class SurveyTest {

    private final Logger logger = LoggerFactory.getLogger(SurveyTest.class);

    @Test
    public void ConstructorTests() {
        logger.info("Running unit test for Survey builder");

        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .build();

        Assert.isTrue(survey1.getCourseId() == courseId);
        Assert.isTrue(survey1.getSurveyId() == surveyId);
        Assert.isTrue(survey1.getSurveyName().equals(surveyName));
    }

    @Test
    public void getSurveyIdTest() {
        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .build();

        Assert.isTrue(survey1.getSurveyId() == surveyId);
    }

    @Test
    public void setSurveyIdTest() {
        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .build();
        long newSurveyId = 2L;
        survey1.setSurveyId(2L);

        Assert.isTrue(survey1.getSurveyId() == newSurveyId);
    }

    @Test
    public void getCourseIdTest() {
        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .build();

        Assert.isTrue(survey1.getCourseId() == courseId);
    }

    @Test
    public void setCourseIdTest() {
        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .build();
        long newCourseId = 2L;
        survey1.setCourseId(newCourseId);
        Assert.isTrue(survey1.getCourseId() == newCourseId);
    }

    @Test
    public void getSurveyNameTest() {
        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .build();

        Assert.isTrue(survey1.getSurveyName().equals(surveyName));
    }

    @Test
    public void setSurveyNameTest() {
        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";
        boolean surveyState = true;

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .build();
        String newSurveyName = "New Survey";
        survey1.setSurveyName(newSurveyName);
        Assert.isTrue(survey1.getSurveyName().equals(newSurveyName));
    }

    @Test
    public void getSurveyStatusTest() {
        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";
        String status = SurveyStatusConstant.UNPUBLISHED.getSurveyStatus();

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .hasStatus(status)
            .build();

        Assert.isTrue(survey1.getStatus().equals(status));
    }

    @Test
    public void setSurveyStatusTest() {
        long surveyId = 1L;
        long courseId = 1L;
        String surveyName = "Test Survey";
        String status = SurveyStatusConstant.UNPUBLISHED.getSurveyStatus();

        Survey survey1 = new Survey.Builder(surveyId)
            .forCourse(courseId)
            .addName(surveyName)
            .hasStatus(status)
            .build();

        String newSurveyStatus = SurveyStatusConstant.PUBLISHED.getSurveyStatus();
        survey1.setStatus(newSurveyStatus);

        Assert.isTrue(survey1.getStatus().equals(newSurveyStatus));
    }
}
