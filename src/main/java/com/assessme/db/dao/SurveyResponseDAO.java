package com.assessme.db.dao;

import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyQuestionResponseData;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: monil Created on: 2020-07-16
 */
public interface SurveyResponseDAO {

    Optional<SurveyQuestionResponseDTO> saveSurveyResponse(
        SurveyQuestionResponseDTO questionResponseDTO) throws Exception;

    Map<Long, List<SurveyQuestionResponseData>> getSurveyResponse(Long surveyId) throws Exception;

}
