package com.assessme.model;

import java.sql.Timestamp;
import java.util.Arrays;
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
public class QuestionTest {

    private final Logger logger = LoggerFactory.getLogger(QuestionTest.class);

    @Test
    public void ConstructorTests() {
        logger.info("Running unit test for QuestionTest constructor");

        Question question = new Question();
        long questionId = 1L;
        long userId = 1L;
        int questionTypeId = 1;
        String questionTitle = "questionTitle";
        String questionText = "questionText";
        String[] optionText = {"option 1", "option 2", "option 3"};
        Timestamp questionDate = new Timestamp(2017 / 10 / 23);
        int[] optionValue = {1, 2, 3};

        question.setQuestionId(questionId);
        question.setUserId(userId);
        question.setQuestionTypeId(questionTypeId);
        question.setQuestionTitle(questionTitle);
        question.setQuestionText(questionText);
        question.setOptionText(optionText);
        question.setQuestionDate(questionDate);
        question.setOptionValue(optionValue);

        Assert.isTrue(question.getQuestionId() == questionId);
        Assert.isTrue(question.getUserId() == userId);
        Assert.isTrue(question.getQuestionTypeId() == questionTypeId);
        Assert.isTrue(question.getQuestionTitle().equals(questionTitle));
        Assert.isTrue(question.getQuestionText().equals(questionText));
        Assert.isTrue(Arrays.equals(question.getOptionText(), optionText));
        Assert.isTrue(question.getQuestionDate() == questionDate);
        Assert.isTrue(Arrays.equals(question.getOptionValue(), optionValue));
    }

    @Test
    public void getQuestionIdTest() {
        logger.info("Running unit test to fetch questionId for QuestionTest constructor");

        Question question = new Question();
        long questionId = 1L;
        question.setQuestionId(questionId);

        Assert.isTrue(question.getQuestionId() == questionId);
    }

    @Test
    public void setQuestionIdTest() {
        logger.info("Running unit test to set questionId for QuestionTest constructor");

        Question question = new Question();
        long questionId = 1L;
        question.setQuestionId(questionId);

        Assert.isTrue(question.getQuestionId() == questionId);
    }

    @Test
    public void getUserIdTest() {
        logger.info("Running unit test to get userId for QuestionTest constructor");

        Question question = new Question();
        long userId = 1L;
        question.setUserId(userId);

        Assert.isTrue(question.getUserId() == userId);
    }

    @Test
    public void setUserIdTest() {
        logger.info("Running unit test to set userId for QuestionTest constructor");

        Question question = new Question();
        long userId = 1L;
        question.setUserId(userId);

        Assert.isTrue(question.getUserId() == userId);
    }

    @Test
    public void getQuestionTypeIdTest() {
        logger.info("Running unit test to get questionTypeId for QuestionTest constructor");

        Question question = new Question();
        int questionTypeId = 1;
        question.setQuestionTypeId(questionTypeId);

        Assert.isTrue(question.getQuestionTypeId() == questionTypeId);
    }

    @Test
    public void setQuestionTypeIdTest() {
        logger.info("Running unit test to set questionTypeId for QuestionTest constructor");

        Question question = new Question();
        int questionTypeId = 1;
        question.setQuestionTypeId(questionTypeId);

        Assert.isTrue(question.getQuestionTypeId() == questionTypeId);
    }

    @Test
    public void getQuestionTitleTest() {
        logger.info("Running unit test to get getQuestionTitle for QuestionTest constructor");

        Question question = new Question();
        String questionTitle = "questionTitle";

        question.setQuestionTitle(questionTitle);

        Assert.isTrue(question.getQuestionTitle().equals(questionTitle));
    }

    @Test
    public void setQuestionTitleTest() {
        logger.info("Running unit test to set getQuestionTitle for QuestionTest constructor");

        Question question = new Question();
        String questionTitle = "questionTitle";

        question.setQuestionTitle(questionTitle);

        Assert.isTrue(question.getQuestionTitle().equals(questionTitle));
    }

    @Test
    public void getQuestionTextTest() {
        logger.info("Running unit test to get getQuestionText for QuestionTest constructor");

        Question question = new Question();
        String questionText = "questionText";

        question.setQuestionText(questionText);

        Assert.isTrue(question.getQuestionText().equals(questionText));
    }

    @Test
    public void setQuestionTextTest() {
        logger.info("Running unit test to set getQuestionText for QuestionTest constructor");

        Question question = new Question();
        String questionText = "questionText";

        question.setQuestionText(questionText);

        Assert.isTrue(question.getQuestionText().equals(questionText));
    }

    @Test
    public void getOptionTextTest() {
        logger.info("Running unit test to get getOptionText for QuestionTest constructor");

        Question question = new Question();
        String[] optionText = {"option 1", "option 2", "option 3"};

        question.setOptionText(optionText);

        Assert.isTrue(Arrays.equals(question.getOptionText(), optionText));
    }

    @Test
    public void setOptionTextTest() {
        logger.info("Running unit test to set getOptionText for QuestionTest constructor");

        Question question = new Question();
        String[] optionText = {"option 1", "option 2", "option 3"};

        question.setOptionText(optionText);

        Assert.isTrue(Arrays.equals(question.getOptionText(), optionText));
    }

    @Test
    public void getOptionValueTest() {
        logger.info("Running unit test to get getOptionValue for QuestionTest constructor");

        Question question = new Question();
        int[] optionValue = {1, 2, 3};

        question.setOptionValue(optionValue);

        Assert.isTrue(Arrays.equals(question.getOptionValue(), optionValue));
    }

    @Test
    public void setOptionValueTest() {
        logger.info("Running unit test to set getOptionValue for QuestionTest constructor");

        Question question = new Question();
        int[] optionValue = {1, 2, 3};

        question.setOptionValue(optionValue);

        Assert.isTrue(Arrays.equals(question.getOptionValue(), optionValue));
    }

    @Test
    public void getQuestionDateTest() {
        logger.info("Running unit test to get getQuestionDate for QuestionTest constructor");

        Question question = new Question();
        Timestamp questionDate = new Timestamp(2017 / 10 / 23);

        question.setQuestionDate(questionDate);

        Assert.isTrue(question.getQuestionDate() == questionDate);
    }

    @Test
    public void setQuestionDateTest() {
        logger.info("Running unit test to set getQuestionDate for QuestionTest constructor");

        Question question = new Question();
        Timestamp questionDate = new Timestamp(2017 / 10 / 23);

        question.setQuestionDate(questionDate);

        Assert.isTrue(question.getQuestionDate() == questionDate);
    }
}
