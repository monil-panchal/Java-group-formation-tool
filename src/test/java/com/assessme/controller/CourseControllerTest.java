package com.assessme.controller;

import com.assessme.model.Course;
import com.assessme.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Optional;

/**
 * @author: hardik
 */

@SpringBootTest
@WebMvcTest
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseservice;

    @MockBean
    private Course course;


    @Test
    public void getCourses() throws Exception {
    	
    	// write test here
    }
    
    @Test
    public void getCourseFromCourseCode() throws Exception {

    	// write test here
    }
    
    @Test
    public void getCourseFromCourseName() throws Exception {

    	// write test here
    }

    @Test
    public void removeCourseByCourseName() throws Exception {

    	// write test here
    }
    
    @Test
    public void removeCourseByCourseCode() throws Exception {

    	// write test here
    }
    
    @Test
    public void addCourse() throws Exception {

    	// write test here
    }

}
