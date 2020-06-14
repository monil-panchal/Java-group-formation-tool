package com.assessme.service;

import com.assessme.model.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * @author: Darshan
 * @created : 2020-06-13
 */

/**
 * Course service layer class for this application
 */
public interface CourseService {


  public Optional<List<Course>> getCourseList() throws Exception;

  public Optional<Course> getCourseWithCode(String courseCode) throws Exception;


  public Optional<Course> getCourseWithName(String courseName) throws Exception;


  public Boolean removeCourseWithCourseCode(String courseCode) throws Exception;

  public Boolean removeCourseWithCourseName(String courseName) throws Exception;

  public Optional<Course> addCourse(Course course) throws Exception;

  public Optional<List<Course>> getCoursesByUserAndRole(long userId, int roleId) throws Exception;
}