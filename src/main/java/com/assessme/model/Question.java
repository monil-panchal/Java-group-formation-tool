package com.assessme.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 2:15 PM
 */
public class Question implements Serializable {

    long questionId;
    long userId;
    int questionTypeId;
    String questionTitle;
    String questionText;
    String[] optionText;
    Timestamp questionDate;
    int[] optionValue;

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String[] getOptionText() {
        return optionText;
    }

    public void setOptionText(String[] optionText) {
        this.optionText = optionText;
    }

    public int[] getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(int[] optionValue) {
        this.optionValue = optionValue;
    }

    public Timestamp getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Timestamp timestamp) {
        this.questionDate = timestamp;
    }
}
