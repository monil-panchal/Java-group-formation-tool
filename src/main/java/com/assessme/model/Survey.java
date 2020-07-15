package com.assessme.model;

import java.sql.Timestamp;

public class Survey {

    private Long surveyId;
    private String surveyName;
    private String description;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long userId;
    private Long courseId;


    public Survey() {
    }

    public Survey(long surveyId, String surveyName, String description, String status, Timestamp createdAt, Timestamp updatedAt, long userId, long courseId) {
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.courseId = courseId;
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
}
