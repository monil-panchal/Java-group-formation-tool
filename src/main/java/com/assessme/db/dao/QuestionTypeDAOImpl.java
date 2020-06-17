package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.QuestionType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 11:49 AM
 */
@Repository
public class QuestionTypeDAOImpl implements QuestionTypeDAO {

  private Logger logger = LoggerFactory.getLogger(QuestionTypeDAOImpl.class);

  @Override
  public Optional<List<QuestionType>> getAllQuestionTypes() throws Exception {
    List<QuestionType> questionTypeList = new ArrayList<>();
    String selectAllQuery = "SELECT * FROM question_type ORDER BY question_type_id";
    try (
        Connection connection = ConnectionManager.getInstance().getDBConnection().get();
        Statement stmt = connection.createStatement()
    ) {
      ResultSet resultSet = stmt.executeQuery(selectAllQuery);
      while(resultSet.next()){
        QuestionType qtype = new QuestionType();
        qtype.setQuestionTypeID(resultSet.getInt(1));
        qtype.setQuestionTypeText(resultSet.getString(2));
        questionTypeList.add(qtype);
        logger.info(String.format("Fetched %s", qtype));
      }
      return Optional.of(questionTypeList);
    } catch (Exception e) {
      logger.error("Error Fetching all QuestionTypes");
      throw e;
    }
  }
}
