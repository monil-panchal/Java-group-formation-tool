//package com.assessme.service;
//
//import com.assessme.model.SurveyQuestionsDetails;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
///**
// * @author: monil
// * Created on: 2020-07-15
// */
//
//@Service
//public class SurveyResponseServiceImpl implements SurveyResponseService {
//
//    private static SurveyResponseServiceImpl instance;
//    private final Logger logger = LoggerFactory.getLogger(SurveyResponseServiceImpl.class);
//
//    public SurveyResponseServiceImpl() {
//    }
//
//    public static SurveyResponseServiceImpl getInstance() {
//        if (instance == null) {
//            instance = new SurveyResponseServiceImpl();
//        }
//        return instance;
//    }
//
//
//    @Override
//    public Optional<SurveyQuestionsDetails> getSurveyQuestionDetails(Long surveyId) throws Exception {
//        return Optional.empty();
//    }
//}
