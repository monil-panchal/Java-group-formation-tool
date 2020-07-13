package com.assessme.model;

public class SurveyQuestions {
    private long surveyId;
//    private long courseId;
    private long questionId;

    public SurveyQuestions(){}

    public SurveyQuestions(long surveyId, long questionId){
        this.surveyId = surveyId;
        this.questionId = questionId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
