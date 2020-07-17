package com.assessme.model;

import com.assessme.util.AppConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author: monil
 * Created on: 2020-07-16
 */
@ExtendWith(MockitoExtension.class)
public class QuestionDetailsDTOTest {

    private final Logger logger = LoggerFactory.getLogger(QuestionDetailsDTOTest.class);

    @Test
    public void BuilderTest() {
        logger.info("Running unit test for QuestionDetailsDTOT builder");

        long questionId = 1L;
        int questionTypeId = 1;
        String questionTypeText = AppConstant.QUESTIONS_TYPE_MCQO;
        String questionText = "Sample question";
        String questionTitle = "This is a sample question";
        List<String> options = List.of("Option-1", "Option-2");

        QuestionDetailsDTO questionDetailsDTO = new QuestionDetailsDTO.Builder(questionId)
                .hasQuestionTypeId(questionTypeId)
                .hasQuestionTypeText(questionTypeText)
                .hasQuestionText(questionText)
                .hasQuestionTitle(questionTitle)
                .hasOptions(options)
                .build();

        Assertions.assertEquals(questionId, questionDetailsDTO.getQuestionId());
        Assertions.assertEquals(questionTypeId, questionDetailsDTO.getQuestionTypeId());
        Assertions.assertEquals(questionTypeText, questionDetailsDTO.getQuestionTypeText());
        Assertions.assertEquals(questionText, questionDetailsDTO.getQuestionText());
        Assertions.assertEquals(questionTitle, questionDetailsDTO.getQuestionTitle());
        Assert.notEmpty(questionDetailsDTO.getOptionText(), "questions option text list should not be not empty");

    }
}
