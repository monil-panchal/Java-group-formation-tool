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
public class SurveyQuestionResponseDataTest {

    private final Logger logger = LoggerFactory.getLogger(SurveyQuestionResponseDataTest.class);

    @Test
    public void BuilderTest() {
        logger.info("Running unit test for SurveyQuestionResponseData builder");

        long questionId = 1L;
        long questionTypeId = 1L;
        String questionTypeText = AppConstant.QUESTIONS_TYPE_MCQO;
        String questionText = "Sample question";
        String questionTitle = "This is a sample question";
        String data = "10";
        Integer value = 10;
        List<String> optionText = List.of("Option-1", "Option-2");
        List<Integer> optionValue = List.of(1, 2);


        SurveyQuestionResponseData questionResponseData = new SurveyQuestionResponseData.Builder(questionId)
                .hasQuestionTypeId(questionTypeId)
                .hasQuestionTypeText(questionTypeText)
                .hasQuestionText(questionText)
                .hasQuestionTitle(questionTitle)
                .hasData(data)
                .hasValue(value)
                .hasOptionText(optionText)
                .hasOptionValue(optionValue)
                .build();

        Assertions.assertEquals(questionId, questionResponseData.getQuestionId());
        Assertions.assertEquals(questionTypeId, questionResponseData.getQuestionTypeId());
        Assertions.assertEquals(questionTypeText, questionResponseData.getQuestionTypeText());
        Assertions.assertEquals(questionText, questionResponseData.getQuestionText());
        Assertions.assertEquals(questionTitle, questionResponseData.getQuestionTitle());
        Assertions.assertEquals(value, questionResponseData.getValue());
        Assert.notEmpty(questionResponseData.getOptionText(), "survey response option text list should not be not empty");
        Assert.notEmpty(questionResponseData.getOptionValue(), "survey response option value list should not be not empty");

    }

}
