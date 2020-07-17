package com.assessme.db.dao;

import com.assessme.model.Question;
import com.assessme.model.User;
import java.util.List;
import java.util.Optional;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 11:47 AM
 */
public interface QuestionDAO {

    void addQuestion(Question question) throws Exception;

    Optional<List<Question>> getQuestionsByUser(User user) throws Exception;

    void removeQuestion(Question question) throws Exception;

    void removeQuestion(long questionId) throws Exception;

    Optional<Question> getQuestionById(long question_id) throws Exception;
}
