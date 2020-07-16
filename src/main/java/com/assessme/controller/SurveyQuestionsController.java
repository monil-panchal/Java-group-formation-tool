package com.assessme.controller;

import com.assessme.model.*;
import com.assessme.service.SurveyQuestionsService;
import com.assessme.service.SurveyQuestionsServiceImpl;
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
 * Created on: 2020-07-15
 */
@RestController
@RequestMapping("/survey_questions")
public class SurveyQuestionsController {

    private final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    private SurveyQuestionsService surveyQuestionsService;

    public SurveyQuestionsController(SurveyQuestionsService surveyQuestionsService) {
        this.surveyQuestionsService = SurveyQuestionsServiceImpl.getInstance();
    }

    @PostMapping(value = "/add_questions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> addSurvey(@RequestBody SurveyQuestionsDTO questionsDTO) {

        logger.info("request:" + questionsDTO);

        logger.info("Calling API for adding questions to the survey.");
        HttpStatus httpStatus = null;
        ResponseDTO<User> responseDTO = null;

        try {
            Optional<SurveyQuestionsDTO> newSurvey = surveyQuestionsService.addQuestionsToSurvey(questionsDTO);
            String resMessage = String
                    .format("Questions are added to the survey :%s",
                            newSurvey.get().getSurveyId());

            responseDTO = new ResponseDTO(true, resMessage, null, newSurvey.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in adding questions to the survey");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }

    @GetMapping(value = "/get_questions")
    public ResponseEntity<ResponseDTO> getQuestionsForSurvey(@RequestParam("surveyId") Long surveyId) {

        logger.info("Calling API for questions retrieval for the survey: " + surveyId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;

        try {
            Optional<SurveyQuestionsDTO> surveyList = surveyQuestionsService.getSurveyQuestions(surveyId);
            String resMessage = String.format("Survey question list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving the survey questions from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }

    @GetMapping(value = "/get_questions_details")
    public ResponseEntity<ResponseDTO> getQuestionsDetailsForSurvey(@RequestParam("surveyId") Long surveyId) {

        logger.info("Calling API for questions details retrieval for the survey: " + surveyId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;

        try {
            Optional<SurveyQuestionsDetails> surveyList = surveyQuestionsService.getSurveyQuestionsDetails(surveyId);
            String resMessage = String.format("Survey question list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving the survey questions from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }
}
