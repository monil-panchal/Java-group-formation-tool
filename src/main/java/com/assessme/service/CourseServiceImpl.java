package com.assessme.service;

import com.assessme.db.dao.CourseDAOImpl;
import com.assessme.model.Course;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: hardik Created on: 2020-05-30
 */

@Service
public class CourseServiceImpl implements CourseService {

    private static CourseServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    private final CourseDAOImpl courseDAOImpl;

    public CourseServiceImpl() {
        this.courseDAOImpl = CourseDAOImpl.getInstance();
    }

    public static CourseServiceImpl getInstance() {
        if (instance == null) {
            instance = new CourseServiceImpl();
        }
        return instance;
    }

    public Optional<List<Course>> getCourseList() throws Exception {

        Optional<List<Course>> courseList = Optional.empty();
        try {
            courseList = Optional.of(courseDAOImpl.getAllCourse());

            String resMessage = String.format("Course list has been retrieved from the database");
            logger.info(resMessage);

        } catch (Exception e) {
            String errMessage = String
                .format("Error in retrieving the course list from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return courseList;
    }

    public Optional<Course> getCourseWithCode(String courseCode) throws Exception {

        Optional<Course> course;
        try {
            course = courseDAOImpl.getCourseByCode(courseCode);
            String resMessage = String
                .format("Course with course code: %s has been retrieved from the database",
                    courseCode);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the course from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return course;
    }

    public Optional<Course> getCourseWithName(String courseName) throws Exception {

        Optional<Course> course;
        try {
            course = courseDAOImpl.getCourseByName(courseName);
            String resMessage = String
                .format("Course with course name: %s has been retrieved from the database",
                    courseName);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the course from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return course;
    }


    public Boolean removeCourseWithCourseCode(String courseCode) throws Exception {

        Boolean removed;
        try {
            removed = courseDAOImpl.removeCourseByCourseCode(courseCode);
            String resMessage = String
                .format("Course with course code: %s has been removing from the database",
                    courseCode);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in removing the course from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return removed;
    }

    public Boolean removeCourseWithCourseName(String courseName) throws Exception {

        Boolean removed;
        try {
            removed = courseDAOImpl.removeCourseByCourseName(courseName);
            String resMessage = String
                .format("Course with course name: %s has been removing from the database",
                    courseName);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in removing the course from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return removed;
    }

    public Optional<Course> addCourse(Course course) throws Exception {

        Optional<Course> newCourse;
        try {
            newCourse = courseDAOImpl.addCourse(course);
            String resMessage = String
                .format("Course with course name: %s has been added to the database",
                    course.getCourseName());
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in adding the course from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return newCourse;
    }

    public Optional<List<Course>> getCoursesByUserAndRole(long userId, int roleId)
        throws Exception {
        Optional<List<Course>> courseList = Optional.empty();
        try {
            courseList = (courseDAOImpl.listCourseByUserAndRole(userId, roleId));
            String resMessage = String.format("Course list has been retrieved from the database");
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String
                .format("Error in retrieving the course list from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return courseList;
    }
}