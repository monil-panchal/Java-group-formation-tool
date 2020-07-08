package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.Question;
import com.assessme.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 11:47 AM
 */
public class QuestionDAOImpl implements QuestionDAO {

  private static QuestionDAOImpl instance;
  final String addQuestion_query = "INSERT INTO questions(user_id, question_type, "
      + "question_title, question_text) values(?,?,?,?)";
  final String addchoiceQuestion_query = "INSERT INTO question_options(question_id, option_text, "
      + "option_value) values (?,?,?);";
  private final Logger logger = LoggerFactory.getLogger(QuestionDAOImpl.class);

  public static QuestionDAOImpl getInstance() {
    if (instance == null) {
      instance = new QuestionDAOImpl();
    }
    return instance;
  }

  @Override
  public void addQuestion(Question question) throws Exception {

    try (
        Connection connection = ConnectionManager.getInstance().getDBConnection().get();
        PreparedStatement stmt = connection
            .prepareStatement(addQuestion_query, PreparedStatement.RETURN_GENERATED_KEYS);
        PreparedStatement stmt_choiceQuestions = connection
            .prepareStatement(addchoiceQuestion_query)
    ) {
      stmt.setLong(1, question.getUserId());
      stmt.setInt(2, question.getQuestionTypeId());
      stmt.setString(3, question.getQuestionTitle());
      stmt.setString(4, question.getQuestionText());
      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        long last_inserted_id;
        last_inserted_id = rs.getLong(1);
        question.setQuestionId(last_inserted_id);
        logger.info("Question Inserted Successfully: " + question.getQuestionTitle());
        if (question.getQuestionTypeId() == 2 || question.getQuestionTypeId() == 3) {
          String[] optionText = question.getOptionText();
          int[] optionValue = question.getOptionValue();
          for (int i = 0; i < optionText.length; i++) {
            stmt_choiceQuestions.setLong(1, last_inserted_id);
            stmt_choiceQuestions.setString(2, optionText[i]);
            stmt_choiceQuestions.setInt(3, optionValue[i]);
            if (!(stmt_choiceQuestions.executeUpdate() > 0)) {
              removeQuestion(question);
              throw new Exception(
                  "Failed to Insert Options for Question:" + question.getQuestionTitle());
            }
          }
        }
      } else {
        throw new Exception("No Insertion");
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error inserting Question: " + question.getQuestionTitle());
      throw e;
    }
  }

  @Override
  public Optional<List<Question>> getQuestionsByUser(User user) throws Exception {
    List<Question> questionList = new ArrayList<>();
    String selectAllQuery = "SELECT * FROM questions WHERE user_id=?";
    try (
        Connection connection = ConnectionManager.getInstance().getDBConnection().get();
        PreparedStatement statement = connection.prepareStatement(selectAllQuery)
    ) {
      statement.setLong(1, user.getUserId());
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Question question = new Question();
        question.setQuestionId(resultSet.getLong(1));
        question.setQuestionTypeId(resultSet.getInt(3));
        question.setQuestionTitle(resultSet.getString(4));
        question.setQuestionText(resultSet.getString(5));
        question.setQuestionDate(resultSet.getTimestamp(6));
        questionList.add(question);
      }
      return Optional.of(questionList);
    } catch (Exception e) {
      logger.error("Error Fetching Questions");
      throw e;
    }
  }

  @Override
  public void removeQuestion(Question question) throws Exception {
    removeQuestion(question.getQuestionId());
  }

  @Override
  public void removeQuestion(long questionId) throws Exception {
    String query = "DELETE FROM questions where question_id=?";
    try (
        Connection connection = ConnectionManager.getInstance().getDBConnection().get();
        PreparedStatement stmt = connection.prepareStatement(query)
    ) {
      stmt.setLong(1, questionId);
      if (stmt.executeUpdate() > 0) {
        logger.info("Question deleted successfully");
      } else {
        throw new Exception("Question deletion failed");
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }
}
