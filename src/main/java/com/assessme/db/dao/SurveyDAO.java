package com.assessme.db.dao;

import com.assessme.model.Survey;
import java.util.List;
import java.util.Optional;

public interface SurveyDAO {

    Optional<Survey> addSurvey(Survey survey) throws Exception;

    List<Survey> getSurveysForCourse(Long courseId) throws Exception;

    Optional<Survey> getSurvey(Long surveyId) throws Exception;

    Optional<Survey> updateSurveyStatus(Survey survey) throws Exception;

}
