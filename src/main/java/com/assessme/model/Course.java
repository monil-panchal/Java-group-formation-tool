package com.assessme.model;

import java.io.Serializable;

/**
 * @author: hardik
 * Created on: 2020-05-30
 */

/**
 * Model bean of the Course table of the database
 */
public class Course implements Serializable{
	
    private int courseId;
    private String courseCode;
    private String courseName;
    
    public Course() {
    }

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }
    
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

}