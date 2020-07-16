package com.assessme.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: monil Created on: 2020-07-16
 */
public class SurveyResponseDTO implements Serializable {

    private Long surveyId;
    private List<UserResponse> users;

    public SurveyResponseDTO() {
    }

    public SurveyResponseDTO(Long surveyId, List<UserResponse> users) {
        this.surveyId = surveyId;
        this.users = users;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "SurveyResponseDTO{" +
            "surveyId=" + surveyId +
            ", users=" + users +
            '}';
    }

    public static class UserResponse {

        private Long userId;
        private List<SurveyQuestionResponseData> questions;

        public UserResponse() {
        }

        public UserResponse(Long userId, List<SurveyQuestionResponseData> questions) {
            this.userId = userId;
            this.questions = questions;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public List<SurveyQuestionResponseData> getQuestions() {
            return questions;
        }

        public void setQuestions(List<SurveyQuestionResponseData> questions) {
            this.questions = questions;
        }

        @Override
        public String toString() {
            return "UserResponse{" +
                "userId=" + userId +
                ", questions=" + questions +
                '}';
        }
    }
}
