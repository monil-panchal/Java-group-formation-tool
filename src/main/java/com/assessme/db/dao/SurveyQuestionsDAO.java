package com.assessme.db.dao;

import com.assessme.model.SurveyQuestions;

import java.util.List;

/**
 * @author: monil
 * Created on: 2020-07-14
 */
public interface SurveyQuestionsDAO {

    List<SurveyQuestions> addSurveyQuestions(List<SurveyQuestions> surveyQuestions) throws Exception;

    List<SurveyQuestions> getSurveyQuestions(long surveyId) throws Exception;

}
