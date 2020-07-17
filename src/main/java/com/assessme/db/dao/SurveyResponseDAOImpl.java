package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyQuestionResponseData;
import com.assessme.util.AppConstant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-07-16
 */
public class SurveyResponseDAOImpl implements SurveyResponseDAO {

    private static final String insertSurveyResponseQuery =
        "INSERT INTO survey_response (survey_id, user_id) " +
            "VALUES(?, ?)";

    private static final String insertSurveyResonseDataValuesQuery =
        "INSERT INTO survey_response_data_values(data) " +
            "VALUES(?)";

    private static final String insertSurveyResponseDataQuery =
        "INSERT INTO survey_response_data (response_id, question_id, data_id)" +
            "VALUES(?, ?, ?)";

    private static final String selectSurveyQuery =
        "SELECT s.response_id, s.survey_id, s.user_id, s.responded_at," +
            "sd.question_id," +
            "qt.question_type_id, qt.qustion_type_text," +
            "q.question_text, q.question_title, srd.data, " +
            "qo.option_value " +
            "FROM survey_response AS s " +
            "INNER JOIN survey_response_data AS sd on s.response_id = sd.response_id " +
            "INNER JOIN survey_response_data_values AS srd on sd.data_id = srd.data_id " +
            "INNER JOIN questions as q on sd.question_id = q.question_id " +
            "INNER JOIN question_type as qt on q.question_type = qt.question_type_id " +
            "LEFT JOIN question_options as qo on qo.option_text = srd.data " +
            "WHERE s.survey_id = ?";

    private static SurveyResponseDAOImpl instance;
    private final Logger logger = LoggerFactory.getLogger(SurveyResponseDAOImpl.class);

    public static SurveyResponseDAOImpl getInstance() {
        if (instance == null) {
            instance = new SurveyResponseDAOImpl();
        }
        return instance;
    }

    @Override
    public Map<Long, List<SurveyQuestionResponseData>> getSurveyResponse(Long surveyId)
        throws Exception {

        Map<Long, List<SurveyQuestionResponseData>> map = new HashMap<>();

        try (
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement preparedStatement = connection
                .prepareStatement(selectSurveyQuery)
        ) {
            preparedStatement.setLong(1, surveyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<SurveyQuestionResponseData> responseDataList = null;

            while (resultSet.next()) {
                if (map.containsKey(resultSet.getLong("user_id"))) {

                    responseDataList = map.get(resultSet.getLong("user_id"));

                    SurveyQuestionResponseData surveyQuestionResponseData = new SurveyQuestionResponseData
                        .Builder(resultSet.getLong("question_id"))
                        .hasQuestionTypeId(resultSet.getLong("question_type_id"))
                        .hasQuestionTypeText(resultSet.getString("qustion_type_text"))
                        .hasQuestionTitle(resultSet.getString("question_title"))
                        .hasQuestionText(resultSet.getString("question_text"))
                        .hasData(resultSet.getString("data"))
                        .hasValue(resultSet.getInt("option_value"))
                        .build();

                    responseDataList.add(surveyQuestionResponseData);

                } else {

                    responseDataList = new ArrayList<>();
                    SurveyQuestionResponseData surveyQuestionResponseData = new SurveyQuestionResponseData
                        .Builder(resultSet.getLong("question_id"))
                        .hasQuestionTypeId(resultSet.getLong("question_type_id"))
                        .hasQuestionTypeText(resultSet.getString("qustion_type_text"))
                        .hasQuestionTitle(resultSet.getString("question_title"))
                        .hasQuestionText(resultSet.getString("question_text"))
                        .hasData(resultSet.getString("data"))
                        .hasValue(resultSet.getInt("option_value"))
                        .build();

                    responseDataList.add(surveyQuestionResponseData);
                }

                map.put(resultSet.getLong("user_id"), responseDataList);

            }
        } catch (
            Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return map;
    }


    @Override
    public Optional<SurveyQuestionResponseDTO> saveSurveyResponse(
        SurveyQuestionResponseDTO questionResponseDTO) throws Exception {
        try (
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement insertResponsePreparedStatement =
                connection
                    .prepareStatement(insertSurveyResponseQuery, Statement.RETURN_GENERATED_KEYS)
        ) {

            List<SurveyQuestionResponseData> surveyQuestionResponseData = questionResponseDTO
                .getResponse();
            Long surveyResponseKey;

            if (questionResponseDTO.getSurveyId() == null
                || questionResponseDTO.getUserId() == null
                || surveyQuestionResponseData == null || surveyQuestionResponseData.size() == 0) {
                throw new Exception(
                    "Survey response has either missing surveyId or userId or question list");
            }

            insertResponsePreparedStatement.setLong(1, questionResponseDTO.getSurveyId());
            insertResponsePreparedStatement.setLong(2, questionResponseDTO.getUserId());

            surveyResponseKey = executePreparedStatement(insertResponsePreparedStatement);
            insertSurveyData(surveyResponseKey, questionResponseDTO);


        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return Optional.of(questionResponseDTO);
    }

    private SurveyQuestionResponseDTO insertSurveyData(Long surveyResponseKey,
        SurveyQuestionResponseDTO questionResponseDTO) throws Exception {

        try (
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement insertDataPreparedStatement =
                connection.prepareStatement(insertSurveyResponseDataQuery,
                    Statement.RETURN_GENERATED_KEYS)
        ) {

            List<SurveyQuestionResponseData> surveyQuestionResponseData = questionResponseDTO
                .getResponse();

            for (SurveyQuestionResponseData questionResponseData : surveyQuestionResponseData) {
                if (AppConstant.QUESTIONS_TYPE_NUMERIC
                    .equalsIgnoreCase(questionResponseData.getQuestionTypeText())
                    || AppConstant.QUESTIONS_TYPE_FREE_TEXT
                    .equalsIgnoreCase(questionResponseData.getQuestionTypeText())) {

                    Long surveyDataValueKey = insertSurveyDataValues(
                        questionResponseData.getData());
                    insertSurveyData(surveyResponseKey, insertDataPreparedStatement,
                        questionResponseData, surveyDataValueKey);

                } else if (AppConstant.QUESTIONS_TYPE_MCQM
                    .equalsIgnoreCase(questionResponseData.getQuestionTypeText())
                    || AppConstant.QUESTIONS_TYPE_MCQO
                    .equalsIgnoreCase(questionResponseData.getQuestionTypeText())) {
                    for (String option : questionResponseData.getOptionText()) {

                        Long surveyDataValueKey = insertSurveyDataValues(option);
                        insertSurveyData(surveyResponseKey, insertDataPreparedStatement,
                            questionResponseData, surveyDataValueKey);
                    }

                } else {
                    throw new Exception("Cannot parse question type");
                }
            }

        } catch (
            Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }

        return questionResponseDTO;
    }

    private Long insertSurveyDataValues(String data) throws Exception {
        Long surveyDataValueKey;

        try (
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement insertDataValuesPreparedStatement =
                connection.prepareStatement(insertSurveyResonseDataValuesQuery,
                    Statement.RETURN_GENERATED_KEYS)
        ) {

            insertDataValuesPreparedStatement.setString(1, data);
            surveyDataValueKey = executePreparedStatement(insertDataValuesPreparedStatement);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return surveyDataValueKey;
    }


    private Long executePreparedStatement(PreparedStatement preparedStatement) throws Exception {
        Long rowId;
        try {
            int dataValueRow = preparedStatement.executeUpdate();

            if (dataValueRow > 0) {
                String successString = String.format("Inserted data successfully");
                logger.info(successString);

            } else {
                String failureString = String.format("Failed to insert data");
                logger.error(failureString);
                throw new Exception(failureString);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rowId = generatedKeys.getLong(1);
                    logger.info("generated rowId: " + rowId);
                } else {
                    throw new SQLException(
                        "Creation of survey data value failed. Cannot obtain id.");
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return rowId;
    }

    private void insertSurveyData(Long surveyResponseKey,
        PreparedStatement insertDataPreparedStatement,
        SurveyQuestionResponseData questionResponseData,
        Long surveyDataValueKey) throws Exception {
        insertDataPreparedStatement.setLong(1, surveyResponseKey);
        insertDataPreparedStatement.setLong(2, questionResponseData.getQuestionId());
        insertDataPreparedStatement.setLong(3, surveyDataValueKey);

        int dataRow = insertDataPreparedStatement.executeUpdate();

        if (dataRow > 0) {
            String successString = String
                .format("Survey data: %s for the question: %s successfully inserted in the DB",
                    questionResponseData.getData(), questionResponseData.getQuestionText());
            logger.info(successString);

        } else {
            String failureString = String
                .format("Failed to insert Survey data: %s for the question: %s",
                    questionResponseData.getData(), questionResponseData.getQuestionText());
            logger.error(failureString);
            throw new Exception(failureString);
        }
    }

}
