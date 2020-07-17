package com.assessme.model;

import java.util.List;

/**
 * @author: monil Created on: 2020-07-15
 */
public class SurveyQuestionsDetails {

    private Long surveyId;
    private List<QuestionDetailsDTO> questions;

    public SurveyQuestionsDetails() {
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public List<QuestionDetailsDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDetailsDTO> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SurveyQuestionsDetails{" +
            "surveyId=" + surveyId +
            ", questions=" + questions +
            '}';
    }
}
