package com.assessme.controller;

import com.assessme.auth.CurrentUserService;
import com.assessme.model.Question;
import com.assessme.model.ResponseDTO;
import com.assessme.model.Survey;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.model.User;
import com.assessme.service.CourseService;
import com.assessme.service.CourseServiceImpl;
import com.assessme.service.QuestionService;
import com.assessme.service.QuestionServiceImpl;
import com.assessme.service.SurveyService;
import com.assessme.service.SurveyServiceImpl;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: monil Created on: 2020-07-14
 */
@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    private final SurveyService surveyService;
    private final CourseService courseService;
    private final CurrentUserService currentUserService;
    private final QuestionService questionService;

    public SurveyController(SurveyService surveyService) {

        this.surveyService = SurveyServiceImpl.getInstance();
        this.courseService = CourseServiceImpl.getInstance();
        this.currentUserService = CurrentUserService.getInstance();
        this.questionService = QuestionServiceImpl.getInstance();
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
            mav.addObject("surveyList", surveyList);
            mav.addObject("courseId", courseId);
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
            mav.addObject("surveyList", surveyList);
            mav.addObject("courseId", courseId);
            Survey.Builder sBuilder = new Survey.Builder(null)
                .forCourse(courseId)
                .createdByUser(currentUserService.getAuthenticatedUser().get().getUserId())
                .hasStatus("unpublished");
            mav.addObject("survey", sBuilder.build());
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
    public ResponseEntity<ResponseDTO> getCourseSurveys(@RequestParam("courseId") Long courseId) {

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

    @PutMapping(value = "/api/change_status", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateSurveyStatusApi(@RequestBody Survey survey) {

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

    @PutMapping(value = "/change_status")
    public ResponseEntity<ResponseDTO> updateSurveyStatus(@RequestParam("surveyId") Long surveyId,
        @RequestParam("status") String status) {

        logger.info("Calling API for updating survey for survey id: " + surveyId);
        HttpStatus httpStatus = null;
        ResponseDTO<List<Survey>> responseDTO = null;
//        long surveyId = Long.parseLong(surveyStr);
        try {
            Survey.Builder survey = new Survey.Builder(surveyId).hasStatus(status);
            logger.info("survey from put mapping" + survey.toString());
            Optional<Survey> updatedSurvey = surveyService.updateSurveyStatus(survey.build());
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

    @GetMapping("/survey_page/{surveyId}")
    public ModelAndView surveyPage(@PathVariable long surveyId) {
        logger.info("in controller method surveyPage");
        ModelAndView mav = new ModelAndView("survey_questions");
        try {
            mav.addObject("survey_id", surveyId);
            logger.info("after adding survey id in model");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String
                .format("Error in retrieving the survey questions from the database");
            logger.error("Error fetching questions for survey_page page");
            mav.addObject("message", errMessage);
        }
        return mav;
    }

    @GetMapping("/user_questions")
    public ModelAndView getQuestions(@RequestParam long surveyId) {
        SurveyQuestionsDTO surveyQuestionsDTO = new SurveyQuestionsDTO();
        ModelAndView mav = new ModelAndView("user_questions");
        mav.addObject("survey_id", surveyId);
        mav.addObject("surveyQuestionsDTO", surveyQuestionsDTO);
        try {
            logger.info("in user_questions endpoint of survey controller");
            User user = currentUserService.getAuthenticatedUser().get();
            logger.info(String.format("Getting Questions for User %d", user.getUserId()));
            List<Question> questionsByUser = questionService.getQuestionsByUser(user).get();
            mav.addObject("questions", questionsByUser);
        } catch (Exception e) {
            logger.error("Error Getting Questions for User");
            mav.addObject("message", "Error Fetching Page");
        }
        return mav;
    }
}
