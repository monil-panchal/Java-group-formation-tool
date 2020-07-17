package com.assessme.service;

import com.assessme.db.dao.SurveyResponseDAO;
import com.assessme.db.dao.SurveyResponseDAOImpl;
import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyQuestionResponseData;
import com.assessme.model.SurveyResponseDTO;
import com.assessme.util.AppConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: monil Created on: 2020-07-15
 */

@Service
public class SurveyResponseServiceImpl implements SurveyResponseService {

    private static SurveyResponseServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(SurveyResponseServiceImpl.class);

    private final SurveyResponseDAO surveyQuestionsDAO;

    public SurveyResponseServiceImpl() {
        this.surveyQuestionsDAO = SurveyResponseDAOImpl.getInstance();
    }

    public static SurveyResponseServiceImpl getInstance() {
        if (instance == null) {
            instance = new SurveyResponseServiceImpl();
        }
        return instance;
    }


    @Override
    public Optional<SurveyQuestionResponseDTO> saveSurveyResponse(
        SurveyQuestionResponseDTO surveyQuestionResponseDTO) throws Exception {
        Optional<SurveyQuestionResponseDTO> optionalSurveyQuestionResponseDTO;
        try {
            optionalSurveyQuestionResponseDTO = surveyQuestionsDAO
                .saveSurveyResponse(surveyQuestionResponseDTO);

            String resMessage = String
                .format("Response of the user: %s for the Survey %s has been added to the database",
                    optionalSurveyQuestionResponseDTO.get().getSurveyId(),
                    optionalSurveyQuestionResponseDTO.get().getUserId());
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format(
                "Error in adding Response of the user: %s for the Survey %s in the database",
                surveyQuestionResponseDTO.getSurveyId(),
                surveyQuestionResponseDTO.getUserId());
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return optionalSurveyQuestionResponseDTO;
    }

    @Override
    public SurveyResponseDTO getSurveyQuestionsForStudent(Long surveyId) throws Exception {
        SurveyResponseDTO responseDTO = new SurveyResponseDTO();

        try {
            if (surveyId == null) {
                throw new Exception("SurveyId cannot be null for fetching response list");
            }
            responseDTO.setSurveyId(surveyId);

            List<SurveyResponseDTO.UserResponse> userResponseList = new ArrayList<>();

            Map<Long, List<SurveyQuestionResponseData>> surveyResponseDTO = surveyQuestionsDAO
                .getSurveyResponse(surveyId);
            for (Map.Entry<Long, List<SurveyQuestionResponseData>> entry : surveyResponseDTO
                .entrySet()) {

                SurveyResponseDTO.UserResponse response = new SurveyResponseDTO.UserResponse();

                List<SurveyQuestionResponseData> questionList = entry.getValue();
                List<SurveyQuestionResponseData> responseDataList = new ArrayList<>();
                Map<Long, List<SurveyQuestionResponseData>> similarQuestionMap = new HashMap<>();

                for (SurveyQuestionResponseData questionResponseData : questionList) {

                    if (AppConstant.QUESTIONS_TYPE_MCQM
                        .equalsIgnoreCase(questionResponseData.getQuestionTypeText())) {

                        if (similarQuestionMap.containsKey(questionResponseData.getQuestionId())) {
                            List<SurveyQuestionResponseData> similarQuestionList = similarQuestionMap
                                .get(questionResponseData.getQuestionId());
                            similarQuestionList.add(questionResponseData);
                        } else {
                            List<SurveyQuestionResponseData> similarQuestionList = new ArrayList<>();
                            similarQuestionList.add(questionResponseData);
                            similarQuestionMap
                                .put(questionResponseData.getQuestionId(), similarQuestionList);
                        }

                    } else if (AppConstant.QUESTIONS_TYPE_MCQO
                        .equalsIgnoreCase(questionResponseData.getQuestionTypeText())) {
                        questionResponseData.setOptionText(List.of(questionResponseData.getData()));
                        questionResponseData
                            .setOptionValue(List.of(questionResponseData.getValue()));
                        responseDataList.add(questionResponseData);
                    } else {
                        responseDataList.add(questionResponseData);
                    }
                }

                for (Map.Entry<Long, List<SurveyQuestionResponseData>> entry1 : similarQuestionMap
                    .entrySet()) {
                    List<String> optionText = new ArrayList<>();
                    List<Integer> optionValue = new ArrayList<>();

                    List<SurveyQuestionResponseData> responseData = entry1.getValue();

                    if (responseData != null || responseData.size() != 0) {
                        for (SurveyQuestionResponseData data : responseData) {
                            optionText.add(data.getData());
                            optionValue.add(data.getValue());
                        }
                    }
                    SurveyQuestionResponseData data = responseData.get(0);
                    data.setOptionValue(optionValue);
                    data.setOptionText(optionText);
                    responseDataList.add(data);
                }
                response.setUserId(entry.getKey());
                response.setQuestions(responseDataList);
                userResponseList.add(response);

            }

            responseDTO.setUsers(userResponseList);

            String resMessage = String
                .format("Response of the survey: %s retrieved successfully", surveyId, responseDTO);
            logger.info(resMessage);

            return responseDTO;
        } catch (Exception e) {
            String errMessage = String
                .format("Error in fetching response for the survey", surveyId);
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }

    }
}
