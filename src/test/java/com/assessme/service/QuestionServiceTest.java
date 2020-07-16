package com.assessme.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.assessme.db.dao.QuestionDAO;
import com.assessme.db.dao.QuestionTypeDAO;
import com.assessme.model.Question;
import com.assessme.model.QuestionType;
import com.assessme.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 5:42 PM
 */
@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    QuestionDAO questionDAO;
    @Mock
    QuestionTypeDAO questionTypeDAO;

    @InjectMocks
    QuestionServiceImpl questionService;

    @Test
    void getAllQuestionType() throws Exception {
        List<QuestionType> questionTypes = new ArrayList<>();
        QuestionType questionType = new QuestionType();
        questionType.setQuestionTypeID(1);
        questionType.setQuestionTypeText("Test");
        questionTypes.add(questionType);
        when(questionTypeDAO.getAllQuestionTypes()).thenReturn(Optional.of(questionTypes));
        assertTrue(questionService.getAllQuestionType().isPresent());
        verify(questionTypeDAO, times(1)).getAllQuestionTypes();
    }

    @Test
    void addQuestion() throws Exception {
        Question question = new Question();
        question.setQuestionTypeId(1);
        question.setQuestionId(1);
        question.setQuestionTitle("Title");
        questionService.addQuestion(question);
        verify(questionDAO, times(1)).addQuestion(question);
    }

    @Test
    void removeQuestion() throws Exception {
        Question question = new Question();
        question.setQuestionTypeId(1);
        question.setQuestionId(1);
        question.setQuestionTitle("Title");
        questionService.removeQuestion(1);
        verify(questionDAO, times(1)).removeQuestion(1);
    }

    @Test
    void getQuestionsByUser() throws Exception {
        Question question = new Question();
        question.setQuestionTypeId(1);
        question.setQuestionId(1);
        question.setQuestionTitle("Title");
        List<Question> questionList = new ArrayList<>();
        questionList.add(question);
        User user = new User();
        user.setUserId(1L);
        when(questionService.getQuestionsByUser(user)).thenReturn(Optional.of(questionList));
        assertTrue(questionService.getQuestionsByUser(user).isPresent());
        verify(questionDAO, times(1)).getQuestionsByUser(user);
    }

}