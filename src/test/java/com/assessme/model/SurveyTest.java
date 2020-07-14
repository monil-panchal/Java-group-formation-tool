//package com.assessme.model;
//
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.util.Assert;
//
///**
// * @author: hardik
// * Created on: 2020-07-13
// */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@SuppressWarnings("deprecation")
//
//public class SurveyTest {
//    private final Logger logger = LoggerFactory.getLogger(RoleTest.class);
//
//    @Test
//    public void ConstructorTests(){
//        logger.info("Running unit test for SurveyTest constructor");
//
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//
//        Assert.isTrue(survey1.getSurveyState());
//        Assert.isTrue(survey1.getCourseId()==courseId);
//        Assert.isTrue(survey1.getSurveyId()==surveyId);
//        Assert.isTrue(survey1.getSurveyName().equals(surveyName));
//    }
//
//    @Test
//    public void getSurveyIdTest(){
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//
//        Assert.isTrue(survey1.getSurveyId()==surveyId);
//    }
//
//    @Test
//    public void setSurveyIdTest(){
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//        long newSurveyId = 2L;
//        survey1.setSurveyId(2L);
//
//        Assert.isTrue(survey1.getSurveyId()==newSurveyId);
//    }
//
//    @Test
//    public void getCourseIdTest(){
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//
//        Assert.isTrue(survey1.getCourseId()==courseId);
//    }
//
//    @Test
//    public void setCourseIdTest(){
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//        long newCourseId = 2L;
//        survey1.setCourseId(newCourseId);
//        Assert.isTrue(survey1.getCourseId()==newCourseId);
//    }
//
//    @Test
//    public void getSurveyNameTest(){
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//
//        Assert.isTrue(survey1.getSurveyName().equals(surveyName));
//    }
//
//    @Test
//    public void setSurveyNameTest(){
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//        String newSurveyName = "New Survey";
//        survey1.setSurveyName(newSurveyName);
//        Assert.isTrue(survey1.getSurveyName().equals(newSurveyName));
//    }
//
//    @Test
//    public void getSurveyStateTest(){
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//
//        Assert.isTrue(survey1.getSurveyState());
//    }
//
//    @Test
//    public void setSurveyStateTest(){
//        long surveyId = 1L;
//        long courseId = 1L;
//        String surveyName = "Test Survey";
//        boolean surveyState = true;
//
//        Survey survey1 = new Survey(surveyId,courseId,surveyName,surveyState);
//        boolean newSurveyState = false;
//        survey1.setSurveyState(newSurveyState);
//
//        Assert.isTrue(!survey1.getSurveyState());
//    }
//}
