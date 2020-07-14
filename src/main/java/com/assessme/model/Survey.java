package com.assessme.model;


public class Survey {
  private long surveyId;
  private long courseId;
  private String surveyName;
  private boolean surveyState;

  public Survey(){
  }

  public Survey(long surveyId, long courseId, String surveyName, boolean surveyState){
    this.surveyId = surveyId;
    this.courseId = courseId;
    this.surveyName = surveyName;
    this.surveyState = surveyState;
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

  public String getSurveyName() {
    return surveyName;
  }

  public void setSurveyName(String surveyName) {
    this.surveyName = surveyName;
  }

  public boolean getSurveyState() {
    return surveyState;
  }

  public void setSurveyState(boolean surveyState) {
    this.surveyState = surveyState;
  }
}
