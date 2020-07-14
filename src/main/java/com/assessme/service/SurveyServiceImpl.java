package com.assessme.service;

import com.assessme.db.dao.SurveyDAOImpl;
import com.assessme.model.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class SurveyServiceImpl implements SurveyService {

    private static CourseServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(SurveyServiceImpl.class);
    private final SurveyDAOImpl surveyDAOImpl;

    public SurveyServiceImpl() {
        this.surveyDAOImpl = SurveyDAOImpl.getInstance();
    }

    public static CourseServiceImpl getInstance() {
        if (instance == null) {
            instance = new CourseServiceImpl();
        }
        return instance;
    }
    @Override
    public Optional<List<Survey>> getSurveyList() throws Exception {
        Optional<List<Survey>> surveyList;
        try{
            surveyList = Optional.of(surveyDAOImpl.getAllSurvey());

            String resMessage = String.format("Survey list has been retrieved from the database");
            logger.info(resMessage);
        }catch (Exception e){
            String errMessage = String.format("Error in retrieving the survey list from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return surveyList;
    }

    @Override
    public Optional<Survey> addSurvey(Survey survey) throws Exception {
        Optional<Survey> newSurvey;
        try {
            newSurvey = surveyDAOImpl.addSurvey(survey);
            String resMessage = String.format("Survey %s for course id: %d has been added to the database", survey.getSurveyName(), survey.getCourseId());
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in adding the survey for course id: %d to the database", survey.getCourseId());
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return newSurvey;
    }

    @Override
    public Optional<Survey> getSurveyByCourse(long courseId) throws Exception {
        Optional<Survey> newSurvey;
        try {
            newSurvey = surveyDAOImpl.getSurveyByCourse(courseId);
            String resMessage = String.format("Survey %s found for course id: %d ", newSurvey.get().getSurveyName(), courseId);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("No survey found for course id: %d in the database", courseId);
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return newSurvey;
    }
}
