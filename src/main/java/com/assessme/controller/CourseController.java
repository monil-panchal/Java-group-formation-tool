package com.assessme.controller;

import com.assessme.model.ResponseDTO;
import com.assessme.model.Course;
import com.assessme.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author: hardik
 * Created on: 2020-05-30
 */

@RestController
@RequestMapping("/course")
public class CourseController {

    private Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    // API endpoint method for fetching all courses
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getCourses() throws Exception {

        logger.info("Calling API for course retrieval.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<Course>> responseDTO = null;

        try {
            Optional<List<Course>> courseList = courseService.getCourseList();
            String resMessage = String.format("course list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, courseList);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the course list from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(responseDTO, httpStatus);
    }

    // API endpoint method for fetching course using courseCode
    @GetMapping()
    public ResponseEntity<ResponseDTO> getCourseFromCourseCode(@RequestParam("courseCode") String courseCode) throws Exception {

        logger.info("Calling API for course retrieval using course code.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<Course>> responseDTO = null;

        try {
            Optional<Course> course = courseService.getCourseWithCode(courseCode);
            String resMessage = String.format("Course has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, course);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the course from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }
    
    // API endpoint method for fetching course using courseCode
    @GetMapping()
    public ResponseEntity<ResponseDTO> getCourseFromCourseName(@RequestParam("courseName") String courseName) throws Exception {

        logger.info("Calling API for course retrieval using course code.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<Course>> responseDTO = null;

        try {
            Optional<Course> course = courseService.getCourseWithName(courseName);
            String resMessage = String.format("Course has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, course);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the course from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }
}
