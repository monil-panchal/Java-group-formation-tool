package com.assessme.service;

import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyResponseDTO;
import java.util.Optional;

/**
 * @author: monil Created on: 2020-07-15
 */
public interface SurveyResponseService {

    Optional<SurveyQuestionResponseDTO> saveSurveyResponse(
        SurveyQuestionResponseDTO surveyQuestionResponseDTO) throws Exception;

    SurveyResponseDTO getSurveyQuestionsForStudent(Long surveyId) throws Exception;

}
