package com.assessme.controller;

import com.assessme.model.*;
import com.assessme.service.SurveyResponseService;
import com.assessme.service.SurveyResponseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-15
 */
@RestController
@RequestMapping("/survey_response")
public class SurveyResponseController {

    private final Logger logger = LoggerFactory.getLogger(SurveyResponseController.class);

    private SurveyResponseService surveyResponseService;

    public SurveyResponseController(SurveyResponseService surveyResponseService) {
        this.surveyResponseService = SurveyResponseServiceImpl.getInstance();
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> addSurvey(@RequestBody  SurveyQuestionResponseDTO questionResponseDTO) {

        logger.info("request:" + questionResponseDTO);

        logger.info("Calling API for adding response to the survey.");
        HttpStatus httpStatus = null;
        ResponseDTO<User> responseDTO = null;

        try {
            Optional<SurveyQuestionResponseDTO> surveryResponse = surveyResponseService.saveSurveyResponse(questionResponseDTO);
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
}
