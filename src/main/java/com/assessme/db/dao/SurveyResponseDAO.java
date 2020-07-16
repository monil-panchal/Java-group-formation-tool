package com.assessme.db.dao;

import com.assessme.model.SurveyQuestionResponseDTO;

import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-16
 */
public interface SurveyResponseDAO {

    Optional<SurveyQuestionResponseDTO> saveSurveyResponse(SurveyQuestionResponseDTO questionResponseDTO) throws Exception;

}
