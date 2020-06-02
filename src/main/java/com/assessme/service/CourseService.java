package com.assessme.service;

import com.assessme.db.dao.CourseDAOImpl;
import com.assessme.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: hardik
 * Created on: 2020-05-30
 */

/**
 * Course service layer class for this application
 */
@Service
public class CourseService {

    private Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseDAOImpl courseDAOImpl;

    public CourseService(CourseDAOImpl courseDAOImpl){
        this.courseDAOImpl = courseDAOImpl;
    }

    /**
     * Service method for retrieving all Courses
     */
    public Optional<List<Course>> getCourseList() throws Exception {

        Optional<List<Course>> courseList = Optional.empty();
        try {
        	courseList = Optional.of(courseDAOImpl.getAllCourse());

            String resMessage = String.format("Course list has been retrieved from the database");
            logger.info(resMessage);

        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the course list from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return courseList;
    }

    /**
     * Service method for retrieving course using courseCode
     */
    public Optional<Course> getCourseWithCode(String courseCode) throws Exception {

        Optional<Course> course;
        try {
            course = courseDAOImpl.getCourseByCode(courseCode);
            String resMessage = String.format("Course with course code: %s has been retrieved from the database", courseCode);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the course from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return course;
    }
    
    /**
     * Service method for retrieving course using courseName
     */
    public Optional<Course> getCourseWithName(String courseName) throws Exception {

        Optional<Course> course;
        try {
            course = courseDAOImpl.getCourseByName(courseName);
            String resMessage = String.format("Course with course name: %s has been retrieved from the database", courseName);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the course from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return course;
    }
}