package com.assessme.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: monil Created on: 2020-07-15
 */

/**
 * This class uses Creational design pattern -  Builder for constructing the POJO object
 */
public class QuestionDetailsDTO implements Serializable {

    private long questionId;
    private int questionTypeId;
    private String questionTypeText;
    private String questionTitle;
    private String questionText;
    private List<String> optionText;

    private QuestionDetailsDTO() {
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

    public static class Builder {

        private final long questionId;
        private int questionTypeId;
        private String questionTypeText;
        private String questionTitle;
        private String questionText;
        private List<String> optionText;

        public Builder(long questionId) {
            this.questionId = questionId;
        }

        public Builder hasQuestionTypeId(int questionTypeId) {
            this.questionTypeId = questionTypeId;
            return this;
        }

        public Builder hasQuestionTypeText(String questionTypeText) {
            this.questionTypeText = questionTypeText;
            return this;
        }

        public Builder hasQuestionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
            return this;
        }

        public Builder hasQuestionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public Builder hasOptions(List<String> optionText) {
            this.optionText = optionText;
            return this;
        }


        public QuestionDetailsDTO build() {
            QuestionDetailsDTO questionDetailsDTO = new QuestionDetailsDTO();
            questionDetailsDTO.questionId = this.questionId;
            questionDetailsDTO.questionTypeId = this.questionTypeId;
            questionDetailsDTO.questionTypeText = this.questionTypeText;
            questionDetailsDTO.questionTitle = this.questionTitle;
            questionDetailsDTO.questionText = this.questionText;
            questionDetailsDTO.optionText = this.optionText;

            return questionDetailsDTO;
        }
    }


}
