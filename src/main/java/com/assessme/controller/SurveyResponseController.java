package com.assessme.controller;

import com.assessme.model.ResponseDTO;
import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyResponseDTO;
import com.assessme.model.User;
import com.assessme.service.SurveyAlgorithmService;
import com.assessme.service.SurveyResponseService;
import com.assessme.service.SurveyResponseServiceImpl;
import com.assessme.service.UserService;
import com.assessme.service.UserServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: monil Created on: 2020-07-15
 */
@RestController
@RequestMapping("/survey_response")
public class SurveyResponseController {

    private final Logger logger = LoggerFactory.getLogger(SurveyResponseController.class);

    private final SurveyResponseService surveyResponseService;
    private final SurveyAlgorithmService surveyAlgorithmService;
    private final UserService userService;

    public SurveyResponseController(SurveyResponseService surveyResponseService) {
        this.surveyResponseService = SurveyResponseServiceImpl.getInstance();
        this.surveyAlgorithmService = SurveyAlgorithmService.getInstance();
        this.userService = UserServiceImpl.getInstance();
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> addSurvey(
        @RequestBody SurveyQuestionResponseDTO questionResponseDTO) {
        logger.info("request:" + questionResponseDTO);
        logger.info("Calling API for adding response to the survey.");
        HttpStatus httpStatus = null;
        ResponseDTO<User> responseDTO = null;

        try {
            Optional<SurveyQuestionResponseDTO> surveryResponse = surveyResponseService
                .saveSurveyResponse(questionResponseDTO);
            String resMessage = String
                .format("Responses are added to the survey :%s",
                    surveryResponse.get().getSurveyId());

            responseDTO = new ResponseDTO(true, resMessage, null, surveryResponse.get());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in adding responses to the survey");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }


    @GetMapping(value = "/get")
    public ResponseEntity<ResponseDTO> getSurvey(@RequestParam("surveyId") Long surveyId) {

        logger.info("Calling API for getting the response of survey: " + surveyId);
        HttpStatus httpStatus = null;
        ResponseDTO<User> responseDTO = null;

        try {
            SurveyResponseDTO surverResponse = surveyResponseService
                .getSurveyQuestionsForStudent(surveyId);
            String resMessage = String
                .format("Survey response has been retrieved from the database");

            responseDTO = new ResponseDTO(true, resMessage, null, surverResponse);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving responses of the survey");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }

    @GetMapping("/survey_group")
    public ModelAndView getGreoupsPage(@RequestParam long surveyId) {
        ModelAndView mav = new ModelAndView("survey_groups.html");
        mav.addObject("surveyId", surveyId);
        try {
            HashMap<Integer, List<Long>> groups = surveyAlgorithmService
                .formGroupsForSurvey(surveyId);
            HashMap<Integer, List<User>> groupUsers = new HashMap<>();

            for (Entry<Integer, List<Long>> e : groups.entrySet()) {
                int groupNumber = e.getKey();
                List<User> userList = e.getValue().stream().map((userId) -> {
                    try {
                        logger.info(String.format("Fetching user with id: %d", userId));
                        return userService.getUserById(userId).get();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
                groupUsers.put(groupNumber, userList);
            }
            mav.addObject("groupMap", groupUsers);
        } catch (Exception e) {
            mav.addObject("message", "Couldn't form groups. check back later");
        }
        return mav;
    }
}
