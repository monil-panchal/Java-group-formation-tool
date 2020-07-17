package com.assessme.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 11:51 AM
 */

public class QuestionType implements Serializable {

    private int questionTypeID;
    private String questionTypeText;

    public int getQuestionTypeID() {
        return questionTypeID;
    }

    public void setQuestionTypeID(int questionTypeID) {
        this.questionTypeID = questionTypeID;
    }

    public String getQuestionTypeText() {
        return questionTypeText;
    }

    public void setQuestionTypeText(String questionTypeText) {
        this.questionTypeText = questionTypeText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionType that = (QuestionType) o;
        return questionTypeID == that.questionTypeID &&
            questionTypeText.equals(that.questionTypeText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionTypeID, questionTypeText);
    }

    @Override
    public String toString() {
        return "QuestionType{" +
            "questionTypeID=" + questionTypeID +
            ", questionTypeText='" + questionTypeText + '\'' +
            '}';
    }
}
