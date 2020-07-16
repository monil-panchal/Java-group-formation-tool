package com.assessme.model;

import java.util.List;

/**
 * @author: monil
 * Created on: 2020-07-16
 */
public class SurveyQuestionResponseData {

    private SurveyQuestionResponseData() {
    }

    public static class Builder {

        private Long questionId;
        private Long questionTypeId;
        private String questionTypeText;
        private String questionText;
        private String questionTitle;
        private String data;
        private List<String> optionText;

        public Builder(Long questionId) {
            this.questionId = questionId;
        }

        public Builder hasQuestionTypeId(Long questionTypeId) {
            this.questionTypeId = questionTypeId;
            return this;
        }

        public Builder hasQuestionTypeText(String questionTypeText) {
            this.questionTypeText = questionTypeText;
            return this;
        }

        public Builder hasQuestionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public Builder hasQuestionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
            return this;
        }

        public Builder hasData(String data) {
            this.data = data;
            return this;
        }

        public Builder hasOptionText(List<String> optionText) {
            this.optionText = optionText;
            return this;
        }

        public SurveyQuestionResponseData build() {
            SurveyQuestionResponseData questionResponseData = new SurveyQuestionResponseData();
            questionResponseData.questionId = this.questionId;
            questionResponseData.questionTitle = this.questionTitle;
            questionResponseData.questionText = this.questionText;
            questionResponseData.data = this.data;
            questionResponseData.questionTypeId = this.questionTypeId;
            questionResponseData.questionTypeText = this.questionTypeText;
            questionResponseData.optionText = this.optionText;

            return questionResponseData;
        }
    }

    private Long questionId;
    private Long questionTypeId;
    private String questionTypeText;
    private String questionText;
    private String questionTitle;
    private String data;
    private List<String> optionText;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(Long questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionTypeText() {
        return questionTypeText;
    }

    public void setQuestionTypeText(String questionTypeText) {
        this.questionTypeText = questionTypeText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getOptionText() {
        return optionText;
    }

    public void setOptionText(List<String> optionText) {
        this.optionText = optionText;
    }

    @Override
    public String toString() {
        return "SurveyQuestionResponseData{" +
                "questionId=" + questionId +
                ", questionTypeId=" + questionTypeId +
                ", questionTypeText='" + questionTypeText + '\'' +
                ", questionText='" + questionText + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", data='" + data + '\'' +
                ", optionText=" + optionText +
                '}';
    }
}
