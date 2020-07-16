package com.assessme.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: hardik Created on: 2020-06-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SuppressWarnings("deprecation")
public class CourseTest {

    private final Logger logger = LoggerFactory.getLogger(CourseTest.class);

    @Test
    public void ConstructorTests() {
        logger.info("Running unit test for course constructor");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course(courseCode, courseName);

        Assert.isTrue(course.getCourseName().equals(courseName));
        Assert.isTrue(course.getCourseCode().equals(courseCode));

        Course course1 = new Course();
        Assert.isNull(course1.getCourseName());
        Assert.isNull(course1.getCourseCode());
    }

    @Test
    public void getCourseIdTest() throws Exception {
        logger.info("Running unit test for fetching course Id");

        long courseId = 7L;
        Course course = new Course();
        course.setCourseId(courseId);
        Assert.isTrue(course.getCourseId() == courseId);
    }

    @Test
    public void setCourseIdTest() throws Exception {
        logger.info("Running unit test for getting course Id");

        long courseId = 7L;
        Course course = new Course();
        course.setCourseId(courseId);
        Assert.isTrue(course.getCourseId() == courseId);
    }

    @Test
    public void getCourseNameTest() throws Exception {
        logger.info("Running unit test for fetching course name");

        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseName(courseName);
        Assert.isTrue(course.getCourseName().equals(courseName));
    }

    @Test
    public void setCourseNameTest() throws Exception {
        logger.info("Running unit test for setting course name");

        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseName(courseName);
        Assert.isTrue(course.getCourseName().equals(courseName));
    }

    @Test
    public void getCourseCodeTest() throws Exception {
        logger.info("Running unit test for fetching course code");

        String courseCode = "CSCI5308";

        Course course = new Course();
        course.setCourseCode(courseCode);
        Assert.isTrue(course.getCourseCode().equals(courseCode));
    }

    @Test
    public void setCourseCodeTest() throws Exception {
        logger.info("Running unit test for setting course code");

        String courseCode = "CSCI5308";

        Course course = new Course();
        course.setCourseCode(courseCode);
        Assert.isTrue(course.getCourseCode().equals(courseCode));
    }
}
