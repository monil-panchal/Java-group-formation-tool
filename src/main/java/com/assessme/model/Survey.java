package com.assessme.model;

import java.sql.Timestamp;

/**
 * @author: monil Created on: 2020-07-14
 */

/**
 * This class uses Creational design pattern -  Builder for constructing the POJO object
 */
public class Survey {

    private Long surveyId;
    private String surveyName;
    private String description;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long userId;
    private Long courseId;

    private Survey() {
    }

    public Long getSurveyId() {

        return surveyId;
    }

    public void setSurveyId(Long surveyId) {

        this.surveyId = surveyId;
    }

    public String getSurveyName() {

        return surveyName;
    }

    public void setSurveyName(String surveyName) {

        this.surveyName = surveyName;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public Timestamp getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {

        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {

        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {

        this.updatedAt = updatedAt;
    }

    public Long getUserId() {

        return userId;
    }

    public void setUserId(Long userId) {

        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Survey{" +
            "surveyId=" + surveyId +
            ", surveyName='" + surveyName + '\'' +
            ", description='" + description + '\'' +
            ", status='" + status + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", userId=" + userId +
            ", courseId=" + courseId +
            '}';
    }

    public static class Builder {

        private final Long surveyId;
        private String surveyName;
        private String description;
        private String status;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private Long userId;
        private Long courseId;

        public Builder(Long surveyId) {
            this.surveyId = surveyId;
        }

        public Survey.Builder addName(String surveyName) {
            this.surveyName = surveyName;
            return this;
        }

        public Survey.Builder addDescription(String description) {
            this.description = description;
            return this;
        }

        public Survey.Builder hasStatus(String status) {
            this.status = status;
            return this;
        }

        public Survey.Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Survey.Builder updatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Survey.Builder createdByUser(Long userId) {
            this.userId = userId;
            return this;
        }

        public Survey.Builder forCourse(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public Survey build() {
            Survey survey = new Survey();
            survey.surveyId = this.surveyId;
            survey.surveyName = this.surveyName;
            survey.description = this.description;
            survey.status = this.status;
            survey.createdAt = this.createdAt;
            survey.updatedAt = this.updatedAt;
            survey.userId = this.userId;
            survey.courseId = this.courseId;

            return survey;
        }

    }
}
