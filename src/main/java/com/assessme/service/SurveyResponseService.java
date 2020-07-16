package com.assessme.service;

import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyQuestionsDetails;

import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-15
 */
public interface SurveyResponseService {

    Optional<SurveyQuestionResponseDTO> saveSurveyQuestionDetails(SurveyQuestionResponseDTO surveyQuestionResponseDTO) throws Exception;
//
//    public void getSurveyQuestionsForStudent(Long surveyId) throws Exception;

}
