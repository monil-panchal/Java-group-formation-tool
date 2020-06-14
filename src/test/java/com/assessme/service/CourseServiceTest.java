package com.assessme.service;

import com.assessme.db.dao.CourseDAOImpl;
import com.assessme.model.Course;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author: hardik
 * Created on: 2020-05-30
 */
@SpringBootTest
public class CourseServiceTest {

    private Logger logger = LoggerFactory.getLogger(CourseServiceTest.class);

    @Mock
    private CourseDAOImpl courseDAO;

    @MockBean
    private Course course;

    // Unit test
    @Test
    public void getCourseWithCodeTest() throws Exception {

        logger.info("Running unit test for fetching course with course code");

        String courseCode = "CSCI5308";
        
        course.setCourseName("Advance Software Development Concepts");
        
        Mockito.when(courseDAO.getCourseByCode(courseCode)).thenReturn(Optional.ofNullable(course));

    }

    //Unit test
    @Test
    public void getCourseListTest() throws Exception {

        logger.info("Running unit test for fetching all courses");

        List<Course> courseList = List.of(course);
        Mockito.when(courseDAO.getAllCourse()).thenReturn(courseList);
        Assert.notEmpty(courseDAO.getAllCourse(), "course list is not null");

    }

}
