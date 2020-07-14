package com.assessme.db.dao;

import com.assessme.model.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyDAO {

    List<Survey> getAllSurvey() throws Exception;

    Optional<Survey> addSurvey(Survey survey) throws Exception;

    Optional<Survey> getSurveyByCourse(long courseId) throws Exception;


}
