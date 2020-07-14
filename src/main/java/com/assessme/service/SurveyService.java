package com.assessme.service;

import com.assessme.model.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyService {
    Optional<List<Survey>> getSurveyList() throws Exception;

    Optional<Survey> addSurvey(Survey survey) throws Exception;

    Optional<Survey> getSurveyByCourse(long courseId) throws Exception;
}
