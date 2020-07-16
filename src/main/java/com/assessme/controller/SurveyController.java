package com.assessme.controller;

import com.assessme.auth.CurrentUserService;
import com.assessme.model.*;
import com.assessme.service.CourseService;
import com.assessme.service.CourseServiceImpl;
import com.assessme.service.SurveyService;
import com.assessme.service.SurveyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-14
 */
@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    private final SurveyService surveyService;
    private final CourseService courseService;
    private final CurrentUserService currentUserService;

    public SurveyController(SurveyService surveyService) {

        this.surveyService = SurveyServiceImpl.getInstance();
        this.courseService = CourseServiceImpl.getInstance();
        this.currentUserService = CurrentUserService.getInstance();
    }

    @PostMapping(value = "/create_survey")
    public ResponseEntity addSurvey(@ModelAttribute("survey") Survey survey) {

        logger.info("request:" + survey);

        logger.info("Calling API for creating a new survey.");
        HttpStatus httpStatus = null;
        ResponseDTO<User> responseDTO = null;

        try {
            Optional<Survey> newSurvey = surveyService.addSurvey(survey);
            String resMessage = String
                    .format("Survey :%s is created successfully for the Course id:%s by the user: %s",
                            survey.getSurveyName(), survey.getCourseId(), survey.getUserId());

            responseDTO = new ResponseDTO(true, resMessage, null, newSurvey.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in creating survey");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(responseDTO, httpStatus);
   }

    @GetMapping(value = "/course_surveys")
    public ModelAndView listSurveysByCourse(@RequestParam("courseId") Long courseId) {

        logger.info("Calling API for survey retrieval for the course: " + courseId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;
        ModelAndView mav = new ModelAndView("student_survey_list");
        try {
            List<Survey> surveyList = surveyService.getSurveysForCourse(courseId);
            String resMessage = String.format("Survey list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList);
            mav.addObject("surveyList",surveyList);
            mav.addObject("courseId",courseId);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving the survey from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }
        return mav;
    }

    @GetMapping(value = "/instructor/course_surveys")
    public ModelAndView listInstructorSurveysByCourse(@RequestParam("courseId") Long courseId) {

        logger.info("Calling API for survey retrieval for the course: " + courseId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;
        ModelAndView mav = new ModelAndView("survey_manager");
        try {
            List<Survey> surveyList = surveyService.getSurveysForCourse(courseId);
            String resMessage = String.format("Survey list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList);
            mav.addObject("surveyList",surveyList);
            mav.addObject("courseId",courseId);
            Survey.Builder sBuilder = new Survey.Builder(null)
                    .forCourse(courseId)
                    .createdByUser(currentUserService.getAuthenticatedUser().get().getUserId())
                    .hasStatus("unpublished");
            mav.addObject("survey",sBuilder.build());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving the survey from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }
        return mav;
    }

    @GetMapping(value = "/api/course_surveys")
    public ResponseEntity<ResponseDTO> getCourseSurveys(@RequestParam("courseId") Long courseId){

        logger.info("Calling API for survey retrieval for the course: " + courseId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;

        try {
            List<Survey> surveyList = surveyService.getSurveysForCourse(courseId);
            String resMessage = String.format("Survey list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving the survey from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }

    @PutMapping(value = "/change_status" , consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateSurveyStatus(@RequestBody Survey survey){

        logger.info("Calling API for updating survey the survey: " + survey);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;

        try {
            Optional<Survey> updatedSurvey = surveyService.updateSurveyStatus(survey);
            String resMessage = String.format("Survey has been updated in the system");
            responseDTO = new ResponseDTO(true, resMessage, null, updatedSurvey.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in updating the survey to the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }
}
