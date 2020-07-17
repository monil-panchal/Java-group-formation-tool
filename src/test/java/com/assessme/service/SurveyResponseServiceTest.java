package com.assessme.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyQuestionResponseData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author: monil Created on: 2020-07-16
 */
@ExtendWith(MockitoExtension.class)
public class SurveyResponseServiceTest {

    private final Logger logger = LoggerFactory.getLogger(SurveyQuestionsServiceTest.class);

    @Mock
    private SurveyResponseServiceImpl surveyResponseService;

    private SurveyQuestionResponseDTO surveyQuestionResponseDTO;

    @BeforeEach
    public void init() {

        surveyQuestionResponseDTO = new SurveyQuestionResponseDTO();
        surveyQuestionResponseDTO.setSurveyId(1L);
        surveyQuestionResponseDTO.setUserId(1L);

        List<SurveyQuestionResponseData> response = new ArrayList<>();
        response.add(new SurveyQuestionResponseData.
            Builder(1L)
            .hasQuestionText("Java rating")
            .hasQuestionTypeId(1L)
            .hasQuestionTypeText("Numeric")
            .hasData("5")
            .build());

        response.add(new SurveyQuestionResponseData.
            Builder(2L)
            .hasQuestionText("Select the technologies you have worked upon.")
            .hasQuestionTypeId(2L)
            .hasQuestionTypeText("Multiplle choice - choose multiple")
            .hasOptionText(List.of("Python", "Java"))
            .build());

        response.add(new SurveyQuestionResponseData.
            Builder(3L)
            .hasQuestionText("Are you a web developer?")
            .hasQuestionTypeId(3L)
            .hasQuestionTypeText("Multiple choice - choose one")
            .hasOptionText(List.of("Yes"))
            .build());

        response.add(new SurveyQuestionResponseData.
            Builder(4L)
            .hasQuestionText("Write your experience")
            .hasQuestionTypeId(3L)
            .hasQuestionTypeText("Free text")
            .hasData("I am good at problem solving")
            .build());

        surveyQuestionResponseDTO.setResponse(response);
    }

    @Test
    public void saveSurveyResponseTest() throws Exception {

        logger.info("Running unit test for saving survey response");

        Optional<SurveyQuestionResponseDTO> optionalSurveyQuestionResponseDTO = Optional
            .of(surveyQuestionResponseDTO);
        Optional<SurveyQuestionResponseDTO> responseDTO;

        Mockito.when(surveyResponseService.saveSurveyResponse(surveyQuestionResponseDTO))
            .thenReturn(optionalSurveyQuestionResponseDTO);
        responseDTO = surveyResponseService.saveSurveyResponse(surveyQuestionResponseDTO);

        assertTrue(surveyResponseService.saveSurveyResponse(surveyQuestionResponseDTO).isPresent());

        Assert.isTrue(responseDTO.isPresent(), "Survey response should not be empty");
        Assert.notEmpty(responseDTO.get().getResponse(),
            "survey question response list should not empty");
        Assert.notNull(responseDTO.get().getSurveyId(), "Survey id should not be null");
        Assert.notNull(responseDTO.get().getUserId(), "User id of the Survey should not be null");

    }
}
