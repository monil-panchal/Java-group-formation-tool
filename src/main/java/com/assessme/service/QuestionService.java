package com.assessme.service;

import com.assessme.model.Question;
import com.assessme.model.QuestionType;
import com.assessme.model.User;
import java.util.List;
import java.util.Optional;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 11:45 AM
 */
public interface QuestionService {

    Optional<List<QuestionType>> getAllQuestionType() throws Exception;

    void addQuestion(Question question) throws Exception;

    Optional<List<Question>> getQuestionsByUser(User user) throws Exception;

    void removeQuestion(long questionId) throws Exception;

    Optional<Question> getQuestionById(Long question_id) throws Exception;
}
