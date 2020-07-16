package com.assessme.service;

import com.assessme.model.SurveyQuestions;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.model.SurveyQuestionsDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-15
 */
@Service
public interface SurveyQuestionsService {

    Optional<SurveyQuestionsDTO> addQuestionsToSurvey(SurveyQuestionsDTO surveyQuestionsDTO) throws Exception;

    Optional<SurveyQuestionsDTO> getSurveyQuestions(Long surveyId) throws Exception;

    Optional<SurveyQuestionsDetails> getSurveyQuestionsDetails(Long surveyId) throws Exception;
}
