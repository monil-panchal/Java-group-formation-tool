package com.assessme.db.dao;

/**
 * @author: hardik
 * Created on: 2020-05-30
 */

import com.assessme.model.Course;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of this class provides CRUD operations for the course table of the database.
 */

public interface CourseDAO {

    Optional<Course> getCourseByCode(String courseCode) throws Exception;

    List<Course> getAllCourse() throws Exception;

    Boolean addCourse(Course course) throws Exception;
    
    Boolean removeCourse(Course course) throws Exception;

    //Optional<Course> updateCourse(Course course) throws Exception;

    Optional<List<Course>> listCourseByUser(long user_id, int roleID) throws Exception;
}