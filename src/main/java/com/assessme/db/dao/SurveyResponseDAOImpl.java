package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyQuestionResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-16
 */
public class SurveyResponseDAOImpl implements SurveyResponseDAO {

    private static final String insertSurveyResponse = "INSERT INTO survey_response (survey_id, user_id) " +
            "VALUES(?, ?)";

    private static final String insertSurveyResonseDataValues = "INSERT INTO survey_response_data_values(data) " +
            "VALUES(?)";

    private static final String insertSurveyResponseData = "INSERT INTO survey_response_data (response_id, question_id, data_id)" +
            "VALUES(?, ?, ?)";

    private static SurveyResponseDAOImpl instance;
    private final Logger logger = LoggerFactory.getLogger(SurveyResponseDAOImpl.class);

    public static SurveyResponseDAOImpl getInstance() {
        if (instance == null) {
            instance = new SurveyResponseDAOImpl();
        }
        return instance;
    }


    @Override
    public Optional<SurveyQuestionResponseDTO> saveSurveyResponse(SurveyQuestionResponseDTO questionResponseDTO) throws Exception {
        try (
                Connection connection = ConnectionManager.getInstance().getDBConnection().get();
                PreparedStatement insertResponsePreparedStatement = connection.prepareStatement(insertSurveyResponse, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement insertDataPreparedStatement = connection.prepareStatement(insertSurveyResponseData, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement insertDataValuesPreparedStatement = connection.prepareStatement(insertSurveyResonseDataValues, Statement.RETURN_GENERATED_KEYS);

        ) {

            List<SurveyQuestionResponseData> surveyQuestionResponseData = questionResponseDTO.getResponse();
            Long surveyResponseKey = 0L;

            if (questionResponseDTO.getSurveyId() == null
                    || questionResponseDTO.getUserId() == null
                    || surveyQuestionResponseData == null || surveyQuestionResponseData.size() == 0) {
                throw new Exception("Survey response has either missing surveyId or userId or question list");
            }

            insertResponsePreparedStatement.setLong(1, questionResponseDTO.getSurveyId());
            insertResponsePreparedStatement.setLong(2, questionResponseDTO.getUserId());

            int row = insertResponsePreparedStatement.executeUpdate();

            if (row > 0) {
                String successString = String.format("Survey: %s for user: %s successfully inserted in the DB",
                        questionResponseDTO.getSurveyId(), questionResponseDTO.getUserId());
                logger.info(successString);

            } else {
                String failureString = String
                        .format("Failed to insert Survey: %s for user: %s",
                                questionResponseDTO.getSurveyId(), questionResponseDTO.getUserId());
                logger.error(failureString);
                throw new Exception(failureString);
            }

            try (ResultSet generatedKeys = insertResponsePreparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    surveyResponseKey = generatedKeys.getLong(1);
                    logger.info("generated surveyResponseKey: "+ surveyResponseKey);
                } else {
                    throw new SQLException("Creation of survey response failed. Cannot obtain id.");
                }
            }

            for (SurveyQuestionResponseData questionResponseData : surveyQuestionResponseData) {
                Long surveyDataValueKey = 0L;
                if ("Numeric".equalsIgnoreCase(questionResponseData.getQuestionTypeText())
                        || "Free text".equalsIgnoreCase(questionResponseData.getQuestionTypeText())) {

                    insertDataValuesPreparedStatement.setString(1, questionResponseData.getData());

                    int dataValueRow = insertDataValuesPreparedStatement.executeUpdate();

                    if (dataValueRow > 0) {
                        String successString = String.format("Survey data value: %s for the question: %s successfully inserted in the DB",
                                questionResponseData.getData(), questionResponseData.getQuestionText());
                        logger.info(successString);

                    } else {
                        String failureString = String
                                .format("Failed to insert Survey data value: %s for the question: %s",
                                        questionResponseData.getData(), questionResponseData.getQuestionText());
                        logger.error(failureString);
                        throw new Exception(failureString);
                    }

                    try (ResultSet generatedKeys = insertDataValuesPreparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            surveyDataValueKey = generatedKeys.getLong(1);
                            logger.info("generated surveyDataValueKey: "+ surveyDataValueKey);
                        } else {
                            throw new SQLException("Creation of survey data value failed. Cannot obtain id.");
                        }
                    }

                    insertDataPreparedStatement.setLong(1, surveyResponseKey);
                    insertDataPreparedStatement.setLong(2, questionResponseData.getQuestionId());
                    insertDataPreparedStatement.setLong(3, surveyDataValueKey);

                    int dataRow = insertDataPreparedStatement.executeUpdate();

                    if (dataRow > 0) {
                        String successString = String.format("Survey data: %s for the question: %s successfully inserted in the DB",
                                questionResponseData.getData(), questionResponseData.getQuestionText());
                        logger.info(successString);

                    } else {
                        String failureString = String
                                .format("Failed to insert Survey data: %s for the question: %s",
                                        questionResponseData.getData(), questionResponseData.getQuestionText());
                        logger.error(failureString);
                        throw new Exception(failureString);
                    }


                } else if ("Multiplle choice - choose multiple".equalsIgnoreCase(questionResponseData.getQuestionTypeText())) {

                } else if ("Multiple choice - choose one".equalsIgnoreCase(questionResponseData.getQuestionTypeText())) {

                } else {
                    throw new Exception("Cannot parse question type");
                }

            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return Optional.of(questionResponseDTO);
    }
}
