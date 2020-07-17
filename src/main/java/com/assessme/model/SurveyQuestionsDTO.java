package com.assessme.model;

import java.util.List;

/**
 * @author: monil Created on: 2020-07-15
 */
public class SurveyQuestionsDTO {

    private Long surveyId;
    private List<Long> questionList;

    public SurveyQuestionsDTO() {
    }

    public SurveyQuestionsDTO(Long surveyId, List<Long> questionList) {
        this.surveyId = surveyId;
        this.questionList = questionList;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public List<Long> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Long> questionList) {
        this.questionList = questionList;
    }

    @Override
    public String toString() {
        return "SurveyQuestionsDTO{" +
            "surveyId=" + surveyId +
            ", questionList=" + questionList +
            '}';
    }
}
