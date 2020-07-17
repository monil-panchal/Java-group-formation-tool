package com.assessme.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: hardik Created on: 2020-06-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SuppressWarnings("deprecation")
public class QuestionTypeTest {

    private final Logger logger = LoggerFactory.getLogger(QuestionTypeTest.class);

    @Test
    public void ConstructorTests() {
        logger.info("Running unit test for QuestionTypeTest constructor");

        QuestionType questionType = new QuestionType();
        int questionTypeID = 1;
        String questionTypeText = "questionTypeText";

        questionType.setQuestionTypeID(questionTypeID);
        questionType.setQuestionTypeText(questionTypeText);

        Assert.isTrue(questionType.getQuestionTypeID() == questionTypeID);
        Assert.isTrue(questionType.getQuestionTypeText().equals(questionTypeText));
    }

    @Test
    public void getQuestionTypeIDTest() {
        logger.info("Running unit test to fetch QuestionTypeID from QuestionTypeTest");

        QuestionType questionType = new QuestionType();
        int questionTypeID = 1;

        questionType.setQuestionTypeID(questionTypeID);

        Assert.isTrue(questionType.getQuestionTypeID() == questionTypeID);
    }

    @Test
    public void setQuestionTypeIDTest() {
        logger.info("Running unit test to set QuestionTypeID from QuestionTypeTest");

        QuestionType questionType = new QuestionType();
        int questionTypeID = 1;

        questionType.setQuestionTypeID(questionTypeID);

        Assert.isTrue(questionType.getQuestionTypeID() == questionTypeID);
    }

    @Test
    public void getQuestionTypeTextTest() {
        logger.info("Running unit test to get QuestionTypeText from QuestionTypeTest");

        QuestionType questionType = new QuestionType();
        String questionTypeText = "questionTypeText";

        questionType.setQuestionTypeText(questionTypeText);

        Assert.isTrue(questionType.getQuestionTypeText().equals(questionTypeText));
    }

    @Test
    public void setQuestionTypeTextTest() {
        logger.info("Running unit test to set QuestionTypeText from QuestionTypeTest");

        QuestionType questionType = new QuestionType();
        String questionTypeText = "questionTypeText";

        questionType.setQuestionTypeText(questionTypeText);

        Assert.isTrue(questionType.getQuestionTypeText().equals(questionTypeText));
    }

}
