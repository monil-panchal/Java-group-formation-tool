package com.assessme.model;

import java.util.List;

/**
 * @author: monil
 * Created on: 2020-07-15
 */
public class QuestionDetailsDTO {

    private long questionId;
    private int questionTypeId;
    private String questionTypeText;
    private String questionTitle;
    private String questionText;
    private List<String> optionText;

    public QuestionDetailsDTO() {
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionTypeText() {
        return questionTypeText;
    }

    public void setQuestionTypeText(String questionTypeText) {
        this.questionTypeText = questionTypeText;
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

    public List<String> getOptionText() {
        return optionText;
    }

    public void setOptionText(List<String> optionText) {
        this.optionText = optionText;
    }

    @Override
    public String toString() {
        return "QuestionDetailsDTO{" +
                "questionId=" + questionId +
                ", questionTypeId=" + questionTypeId +
                ", questionTypeText='" + questionTypeText + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", questionText='" + questionText + '\'' +
                ", optionText=" + optionText +
                '}';
    }
}
