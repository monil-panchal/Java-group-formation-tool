package com.assessme.controller;

import com.assessme.auth.CurrentUserService;
import com.assessme.model.*;
import com.assessme.model.ResponseDTO;
import com.assessme.model.Survey;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.model.SurveyQuestionsDetails;
import com.assessme.model.User;
import com.assessme.service.SurveyQuestionsService;
import com.assessme.service.SurveyQuestionsServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: monil Created on: 2020-07-15
 */
@RestController
@RequestMapping("/survey_questions")
public class SurveyQuestionsController {

    private final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    private SurveyQuestionsService surveyQuestionsService;
    CurrentUserService currentUserService;

    public SurveyQuestionsController(SurveyQuestionsService surveyQuestionsService) {
        this.surveyQuestionsService = SurveyQuestionsServiceImpl.getInstance();
        this.currentUserService = CurrentUserService.getInstance();
    }

    @PostMapping(value = "/add_questions")
    public ResponseEntity<ResponseDTO> addSurvey(@RequestParam("surveyId") Long surveyId,
        @RequestParam("questionList") String questionList) {

        logger.info(String
            .format("in add questions, surveyid: %d, questionList: %s", surveyId, questionList));
        logger.info("Calling API for adding questions to the survey.");
        HttpStatus httpStatus = null;
        ResponseDTO<User> responseDTO = null;

        SurveyQuestionsDTO questionsDTO = new SurveyQuestionsDTO();
        questionList = questionList.replace("[", "");
        questionList = questionList.replace("]", "");
        questionList = questionList.replace("\"", "");
        List<String> myList = new ArrayList<String>(Arrays.asList(questionList.split(",")));
        List<Long> quesList = new ArrayList<>();
        for (String element : myList) {
            quesList.add(Long.parseLong(element));
        }

        questionsDTO.setSurveyId(surveyId);
        questionsDTO.setQuestionList(quesList);

        try {
            Optional<SurveyQuestionsDTO> newSurvey = surveyQuestionsService
                .addQuestionsToSurvey(questionsDTO);
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
    public ResponseEntity<ResponseDTO> getQuestionsForSurvey(
        @RequestParam("surveyId") Long surveyId) {

        logger.info("Calling API for questions retrieval for the survey: " + surveyId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;

        try {
            Optional<SurveyQuestionsDTO> surveyList = surveyQuestionsService
                .getSurveyQuestions(surveyId);
            String resMessage = String
                .format("Survey question list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String
                .format("Error in retrieving the survey questions from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }

    @GetMapping(value = "/get_questions_details")
    public ModelAndView getQuestionsDetailsBySurveyId(@RequestParam("surveyId") Long surveyId) {

        logger.info("Calling API for questions details retrieval for the survey: " + surveyId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;
        ModelAndView modelAndView = new ModelAndView("student_survey");
        try {
            Optional<SurveyQuestionsDetails> surveyList = surveyQuestionsService.getSurveyQuestionsDetails(surveyId);
            String resMessage = String.format("Survey question list has been retrieved from the database");
            modelAndView.addObject("surveyQuestions",surveyList.get().getQuestions());
            modelAndView.addObject("userId", currentUserService.getAuthenticatedUser().get().getUserId());
            modelAndView.addObject("surveyId",surveyList.get().getSurveyId());
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String
                .format("Error in retrieving the survey questions from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return modelAndView;
    }

    @GetMapping(value = "/api/get_questions_details")
    public ResponseEntity<ResponseDTO> getQuestionsDetailsForSurvey(
        @RequestParam("surveyId") Long surveyId) {

        logger.info("Calling API for questions details retrieval for the survey: " + surveyId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;
        try {
            Optional<SurveyQuestionsDetails> surveyList = surveyQuestionsService
                .getSurveyQuestionsDetails(surveyId);
            String resMessage = String
                .format("Survey question list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String
                .format("Error in retrieving the survey questions from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }

    @GetMapping(value = "/instructor/get_questions_details")
    public ModelAndView getQuestionsDetailsForSurveyForInstructor(
        @RequestParam("surveyId") Long surveyId) {

        logger.info("Calling API for questions details retrieval for the survey: " + surveyId);
        ModelAndView mav = new ModelAndView("survey_questions");
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;

        try {
            mav.addObject("survey_id", surveyId);
//            logger.info("after adding survey id in model");
            Optional<SurveyQuestionsDetails> surveyList = surveyQuestionsService
                .getSurveyQuestionsDetails(surveyId);
            String resMessage = String
                .format("Survey question list has been retrieved from the database");
            mav.addObject("questions", surveyList.get());
            mav.addObject("surveyQuestions",surveyList.get().getQuestions());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String
                .format("Error in retrieving the survey questions from the database");
            logger.error("Error fetching questions for survey_page page");
            mav.addObject("message", errMessage);
        }

        return mav;
    }

    @GetMapping(value = "/instructor/get_questions")
    public ModelAndView getQuestionsForSurveyInstructor(@RequestParam("surveyId") Long surveyId) {

        logger.info("Calling API for questions retrieval for the survey: " + surveyId);
        HttpStatus httpStatus = null;
        ModelAndView mav = new ModelAndView("survey_questions");
        ResponseDTO<List<Survey>> responseDTO = null;

        try {
            mav.addObject("survey_id", surveyId);
            Optional<SurveyQuestionsDTO> surveyList = surveyQuestionsService.getSurveyQuestions(surveyId);
            String resMessage = String.format("Survey question list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, surveyList.get());
//            mav.addObject("questions", surveyList.get());
//            mav.addObject("questions", new ArrayList<>());
//            for(long id: surveyList.get())
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving the survey questions from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return mav;
    }
}
