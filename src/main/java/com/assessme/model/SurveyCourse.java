package com.assessme.model;

/**
 * @author: monil
 * Created on: 2020-07-14
 */
public class SurveyCourse {

    private long surveyId;
    private long courseId;

    public SurveyCourse() {
    }

    public SurveyCourse(long surveyId, long courseId) {
        this.surveyId = surveyId;
        this.courseId = courseId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "SurveyCourse{" +
                "surveyId=" + surveyId +
                ", courseId=" + courseId +
                '}';
    }
}
