package com.assessme.model;

/**
 * @author: monil
 * Created on: 2020-07-14
 */
public class SurveyResponse {

    private long responseId;
    private String response;

    public SurveyResponse() {
    }

    public SurveyResponse(long responseId, String response) {
        this.responseId = responseId;
        this.response = response;
    }

    public long getResponseId() {
        return responseId;
    }

    public void setResponseId(long responseId) {
        this.responseId = responseId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SurveyResponse{" +
                "responseId=" + responseId +
                ", response='" + response + '\'' +
                '}';
    }
}
