package com.assessme.util;

/**
 * @author: monil Created on: 2020-07-14
 */
public enum SurveyStatusConstant {

    PUBLISHED("published"),
    UNPUBLISHED("unpublished");

    private final String surveyStatus;

    SurveyStatusConstant(String surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    public static SurveyStatusConstant fromString(String status) {
        for (SurveyStatusConstant statusConstant : SurveyStatusConstant.values()) {
            if (statusConstant.surveyStatus.equalsIgnoreCase(status)) {
                return statusConstant;
            }
        }
        throw new IllegalArgumentException("No survey status: " + status + " found");
    }

    public String getSurveyStatus() {
        return surveyStatus;
    }
}
