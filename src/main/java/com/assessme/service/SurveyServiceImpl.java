package com.assessme.service;

import com.assessme.db.dao.SurveyDAOImpl;
import com.assessme.model.Survey;
import com.assessme.util.SurveyStatusConstant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceImpl implements SurveyService {

    private static SurveyServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(SurveyServiceImpl.class);
    private final SurveyDAOImpl surveyDAOImpl;

    public SurveyServiceImpl() {
        this.surveyDAOImpl = SurveyDAOImpl.getInstance();
    }

    public static SurveyServiceImpl getInstance() {
        if (instance == null) {
            instance = new SurveyServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Survey> addSurvey(Survey survey) throws Exception {
        Optional<Survey> newSurvey;
        try {

            List<Survey> surveyList = this.getSurveysForCourse(survey.getCourseId());
            if (surveyList.size() > 0) {
                throw new Exception("Survey for this course has already being created");
            }

            survey.setStatus(SurveyStatusConstant.UNPUBLISHED.getSurveyStatus());
            newSurvey = surveyDAOImpl.addSurvey(survey);
            String resMessage = String
                .format("Survey %s for course id: %s has been added to the database",
                    survey.getSurveyName(), survey.getCourseId());
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String
                .format("Error in adding the survey for course id: %s to the database",
                    survey.getCourseId());
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return newSurvey;
    }

    @Override
    public List<Survey> getSurveysForCourse(Long courseId) throws Exception {
        List<Survey> surveyList = null;
        try {
            if (courseId == null) {
                throw new Exception("CourseId cannot be null for fetching survey list");
            }
            surveyList = surveyDAOImpl.getSurveysForCourse(courseId);
            String resMessage = String.format("Survey list has been retrieved from the database");
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String
                .format("Error in retrieving the Survey list from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return surveyList;
    }

    @Override
    public Optional<Survey> getSurvey(Long surveyId) throws Exception {
        Optional<Survey> survey = Optional.empty();
        try {

            if (surveyId == null) {
                throw new Exception("Survey Id cannot be null for fetching a survey");
            }
            survey = surveyDAOImpl.getSurvey(surveyId);

            if (survey.isPresent()) {
                String resMessage = String
                    .format("Survey: %s has been retrieved from the system,", survey.get());
                logger.info(resMessage);
            }

        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the survey from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return survey;
    }

    @Override
    public Optional<Survey> updateSurveyStatus(Survey survey) throws Exception {
        Optional<Survey> updatedSurvey = Optional.empty();
        Optional<Survey> surveyFromDB = Optional.empty();
        try {

            if (survey == null || survey.getSurveyId() == null) {
                throw new Exception("Survey Id cannot be null for updating a survey");
            }

            surveyFromDB = this.getSurvey(survey.getSurveyId());

            if (surveyFromDB.isPresent()) {
                if (surveyFromDB.get().getStatus()
                    .equalsIgnoreCase(SurveyStatusConstant.UNPUBLISHED.getSurveyStatus())) {
                    String resMessage = String
                        .format("Survey: %s status has been changed to: %s in the system",
                            survey.getSurveyName(),
                            survey.getStatus());
                    logger.info(resMessage);
                    updatedSurvey = surveyDAOImpl.updateSurveyStatus(survey);
                    return updatedSurvey;
                } else {
                    String errMessage = String
                        .format("Survey: %s 's status is already: %s and hence cannot be updated",
                            surveyFromDB.get().getSurveyName(), surveyFromDB.get().getStatus());
                    throw new Exception(errMessage);
                }
            } else {
                throw new Exception(
                    "Survey not found in the system for updating the status: " + survey
                        .getSurveyId());
            }


        } catch (Exception e) {
            String errMessage = String.format("Error in updating the survey in the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
    }

}
