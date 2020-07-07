package com.assessme.service;

import com.assessme.db.dao.QuestionDAO;
import com.assessme.db.dao.QuestionDAOImpl;
import com.assessme.db.dao.QuestionTypeDAO;
import com.assessme.db.dao.QuestionTypeDAOImpl;
import com.assessme.model.Question;
import com.assessme.model.QuestionType;
import com.assessme.model.User;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 11:46 AM
 */
@Service
public class QuestionServiceImpl implements QuestionService {

  QuestionDAO questionDAO;
  QuestionTypeDAO questionTypeDAO;
  private final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

  public QuestionServiceImpl() {
    this.questionDAO = QuestionDAOImpl.getInstance();
    this.questionTypeDAO = QuestionTypeDAOImpl.getInstance();
  }
  private static QuestionServiceImpl instance;

  public static QuestionServiceImpl getInstance(){
      if(instance == null){
          instance = new QuestionServiceImpl();
      }
      return instance;
  }
  @Override
  public Optional<List<QuestionType>> getAllQuestionType() throws Exception {
    return questionTypeDAO.getAllQuestionTypes();
  }

  @Override
  public void addQuestion(Question question) throws Exception {
    questionDAO.addQuestion(question);
    logger.info("Question Successfully Added");
  }

  @Override
  public Optional<List<Question>> getQuestionsByUser(User user) throws Exception {
    return questionDAO.getQuestionsByUser(user);
  }

  @Override
  public void removeQuestion(long questionId) throws Exception {
    questionDAO.removeQuestion(questionId);
  }

}
