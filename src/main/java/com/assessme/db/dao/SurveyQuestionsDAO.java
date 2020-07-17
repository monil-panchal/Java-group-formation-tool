package com.assessme.db.dao;

import com.assessme.model.SurveyQuestions;
import com.assessme.model.SurveyQuestionsDTO;
import java.util.List;
import java.util.Optional;

/**
 * @author: monil Created on: 2020-07-14
 */
public interface SurveyQuestionsDAO {

    Optional<SurveyQuestionsDTO> addSurveyQuestions(SurveyQuestionsDTO surveyQuestions)
        throws Exception;

    List<SurveyQuestions> getSurveyQuestions(long surveyId) throws Exception;

}
