package com.assessme.controller;

import com.assessme.model.ResponseDTO;
import com.assessme.model.Survey;
import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import com.assessme.service.SurveyService;
import com.assessme.service.SurveyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = SurveyServiceImpl.getInstance();
    }


    @PostMapping(value = "/create_survey", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> addSurvey(@RequestBody Survey survey) {

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
