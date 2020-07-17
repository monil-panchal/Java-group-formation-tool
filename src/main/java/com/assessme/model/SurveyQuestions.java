package com.assessme.model;

public class SurveyQuestions {

    private Long surveyId;
    private Long questionId;

    public SurveyQuestions() {
    }

    public SurveyQuestions(long surveyId, long questionId) {
        this.surveyId = surveyId;
        this.questionId = questionId;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
