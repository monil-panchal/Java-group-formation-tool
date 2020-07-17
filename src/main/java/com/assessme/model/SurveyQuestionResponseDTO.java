package com.assessme.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: monil Created on: 2020-07-16
 */
public class SurveyQuestionResponseDTO implements Serializable {

    private Long surveyId;
    private Long userId;
    private List<SurveyQuestionResponseData> response;

    public SurveyQuestionResponseDTO() {
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<SurveyQuestionResponseData> getResponse() {
        return response;
    }

    public void setResponse(List<SurveyQuestionResponseData> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SurveyQuestionResponseDTO{" +
            "surveyId=" + surveyId +
            ", userId=" + userId +
            ", response=" + response +
            '}';
    }
}
