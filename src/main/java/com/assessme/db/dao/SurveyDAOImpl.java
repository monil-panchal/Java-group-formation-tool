package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.Survey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyDAOImpl implements SurveyDAO {

    private static final String insertSurveyQuery =
        "INSERT INTO survey(survey_name, description, status, created_at, updated_at, user_id, course_id) "
            +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static SurveyDAOImpl instance;
    final String getSurveysForCourseQuery =
        "SELECT * FROM survey WHERE course_id=?";
    final String getSurveyQuery =
        "SELECT * FROM survey WHERE survey_id=?";
    final String updateSurveyStatusQuery = "UPDATE survey set status = ?, updated_at = ? where survey_id = ?";
    private final Logger logger = LoggerFactory.getLogger(SurveyDAOImpl.class);

    public static SurveyDAOImpl getInstance() {
        if (instance == null) {
            instance = new SurveyDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<Survey> addSurvey(Survey survey) throws Exception {

        try (
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement preparedStatement = connection.prepareStatement(insertSurveyQuery)
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
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement preparedStatement = connection
                .prepareStatement(getSurveysForCourseQuery)
        ) {
            preparedStatement.setLong(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Survey survey = new Survey.Builder(resultSet.getLong("survey_id"))
                    .addDescription(resultSet.getString("description"))
                    .addName(resultSet.getString("survey_name"))
                    .createdByUser(resultSet.getLong("user_id"))
                    .forCourse(resultSet.getLong("course_id"))
                    .createdAt(resultSet.getTimestamp("created_at"))
                    .updatedAt(resultSet.getTimestamp("updated_at"))
                    .hasStatus(resultSet.getString("status"))

                    .build();

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
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
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
                    .format("Failed to update the survey with id: %s record in the DB",
                        survey.getSurveyId());
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
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement statement = connection.prepareStatement(getSurveyQuery)
        ) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            logger.info(String.format("Survey retrieved successfully"));
            while (resultSet.next()) {

                Survey surveyFromDB = new Survey.Builder(resultSet.getLong("survey_id"))
                    .addDescription(resultSet.getString("description"))
                    .addName(resultSet.getString("survey_name"))
                    .createdByUser(resultSet.getLong("user_id"))
                    .forCourse(resultSet.getLong("course_id"))
                    .createdAt(resultSet.getTimestamp("created_at"))
                    .updatedAt(resultSet.getTimestamp("updated_at"))
                    .hasStatus(resultSet.getString("status"))

                    .build();

                survey = Optional.of(surveyFromDB);

                String successString = String
                    .format("Survey: %s retrieved successfully.", survey.get());
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
