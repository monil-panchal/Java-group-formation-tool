package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.SurveyQuestions;
import com.assessme.model.SurveyQuestionsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyQuestionsDAOImpl implements SurveyQuestionsDAO {

    private static final String insertSurveyQuestionsQuery =
        "REPLACE INTO survey_questions(survey_id, question_id) " +
            "VALUES(?, ?)";
    private static SurveyQuestionsDAOImpl instance;
    final String getSurveysQuestionsQuery =
        "SELECT * FROM survey_questions WHERE survey_id=?";
    private final Logger logger = LoggerFactory.getLogger(SurveyQuestionsDAOImpl.class);

    public static SurveyQuestionsDAOImpl getInstance() {
        if (instance == null) {
            instance = new SurveyQuestionsDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<SurveyQuestionsDTO> addSurveyQuestions(SurveyQuestionsDTO surveyQuestions)
        throws Exception {
        try (
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement preparedStatement = connection
                .prepareStatement(insertSurveyQuestionsQuery)
        ) {

            List<Long> questions = surveyQuestions.getQuestionList();
            for (Long questionIds : questions) {
                preparedStatement.setLong(1, surveyQuestions.getSurveyId());
                preparedStatement.setLong(2, questionIds);

                preparedStatement.addBatch();
            }

            int[] row = preparedStatement.executeBatch();
            if (row == null || row.length == 0) {

                String failureString = String
                    .format("Failed to insert questions for the Survey:%s",
                        surveyQuestions.getSurveyId());
                logger.error(failureString);
                throw new Exception(failureString);
            } else if (row.length < questions.size()) {
                String successString = String
                    .format("Only few questions for the Survey :%s were inserted: %s",
                        surveyQuestions.getSurveyId());
                logger.info(successString);
            } else {
                String successString = String
                    .format("Questions were added to the Survey :%s",
                        surveyQuestions.getSurveyId());
                logger.info(successString);
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return Optional.of(surveyQuestions);
    }

    @Override
    public List<SurveyQuestions> getSurveyQuestions(long surveyId) throws Exception {

        List<SurveyQuestions> surveyQuestions = new ArrayList<>();
        try (
            Connection connection = ConnectionManager.getInstance().getDBConnection().get();
            PreparedStatement preparedStatement = connection
                .prepareStatement(getSurveysQuestionsQuery)
        ) {
            preparedStatement.setLong(1, surveyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SurveyQuestions questions = new SurveyQuestions();
                questions.setSurveyId(resultSet.getLong("survey_id"));
                questions.setQuestionId(resultSet.getLong("question_id"));

                surveyQuestions.add(questions);
            }
            logger.info(String
                .format("Survey questions list retrieved from the database: %s", surveyQuestions));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return surveyQuestions;

    }
}
