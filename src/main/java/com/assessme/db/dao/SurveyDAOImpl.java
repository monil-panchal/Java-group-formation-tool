package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class SurveyDAOImpl implements SurveyDAO {

    private static SurveyDAOImpl instance;
    private final Logger logger = LoggerFactory.getLogger(SurveyDAOImpl.class);
    private final ConnectionManager connectionManager;

    public SurveyDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    public static SurveyDAOImpl getInstance() {
        if (instance == null) {
            instance = new SurveyDAOImpl();
        }
        return instance;
    }

    private static final String insertSurveyQuery = "INSERT INTO survey(survey_name, description, status, created_at, updated_at, user_id, course_id) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";

    final String getSurveysForCourseQuery =
            "SELECT * FROM survey WHERE course_id=?";

    final String getSurveyQuery =
            "SELECT * FROM survey WHERE survey_id=?";

    final String updateSurveyStatusQuery = "UPDATE survey set status = ?, updated_at = ? where survey_id = ?";

    @Override
    public Optional<Survey> addSurvey(Survey survey) throws Exception {

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection.prepareStatement(insertSurveyQuery);
        ) {

            preparedStatement.setString(1, survey.getSurveyName());
            preparedStatement.setString(2, survey.getDescription());
            preparedStatement.setString(3, survey.getStatus());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));
            preparedStatement.setLong(6, survey.getUserId());
            preparedStatement.setLong(7, survey.getCourseId());



            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                String successString = String
                        .format("Survey :%s inserted successfully for the Course id:%s by the user: %s",
                                survey.getSurveyName(), survey.getCourseId(), survey.getUserId());
                logger.info(successString);

            } else {
                String failureString = String
                        .format("Survey: %s insertion failed for the Course Id:%s",
                                survey.getSurveyName(), survey.getCourseId());
                logger.error(failureString);
                throw new Exception(failureString);
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return Optional.of(survey);
    }

    @Override
    public List<Survey> getSurveysForCourse(Long courseId) throws Exception {

        List<Survey> surveyList = new ArrayList<>();
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(getSurveysForCourseQuery)
        ) {
            preparedStatement.setLong(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Survey survey = new Survey();
                survey.setSurveyId(resultSet.getLong("survey_id"));
                survey.setCourseId(resultSet.getLong("course_id"));
                survey.setUserId(resultSet.getLong("user_id"));
                survey.setCreatedAt(resultSet.getTimestamp("created_at"));
                survey.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                survey.setStatus(resultSet.getString("status"));
                survey.setSurveyName(resultSet.getString("survey_name"));
                survey.setDescription(resultSet.getString("description"));

                surveyList.add(survey);
            }
            logger.info(String.format("Survey list retrieved from the database: %s", surveyList));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return surveyList;

    }

    @Override
    public Optional<Survey> updateSurveyStatus(Survey survey) throws Exception {
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(updateSurveyStatusQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, survey.getStatus());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
            preparedStatement.setLong(3, survey.getSurveyId());

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                String successString = String
                        .format("Survey record with id: %s has been successfully updated in the DB",
                                survey.getSurveyId());
                logger.info(successString);

            } else {
                String failureString = String
                        .format("Failed to update the survey with id: %s record in the DB", survey.getSurveyId());
                logger.error(failureString);
                throw new Exception(failureString);
            }
            return Optional.of(survey);
        } catch (Exception e) {

            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Optional<Survey> getSurvey(Long id) throws Exception {
        Optional<Survey> survey = Optional.empty();

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement statement = connection.prepareStatement(getSurveyQuery)
        ) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            logger.info(String.format("Survey retrieved successfully"));
            while (resultSet.next()) {
                Survey surveyFromDB = new Survey();
                surveyFromDB.setSurveyId(resultSet.getLong("survey_id"));
                surveyFromDB.setCourseId(resultSet.getLong("course_id"));
                surveyFromDB.setUserId(resultSet.getLong("user_id"));
                surveyFromDB.setCreatedAt(resultSet.getTimestamp("created_at"));
                surveyFromDB.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                surveyFromDB.setStatus(resultSet.getString("status"));
                surveyFromDB.setSurveyName(resultSet.getString("survey_name"));
                surveyFromDB.setDescription(resultSet.getString("description"));

                survey = Optional.of(surveyFromDB);

                String successString = String.format("Survey: %s retrieved successfully.", survey.get());
                logger.info(successString);

            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return survey;
    }
}
