package com.assessme.service;

import com.assessme.db.dao.SurveyQuestionsDAOImpl;
import com.assessme.db.dao.SurveyResponseDAO;
import com.assessme.db.dao.SurveyResponseDAOImpl;
import com.assessme.model.Survey;
import com.assessme.model.SurveyQuestionResponseDTO;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.util.SurveyStatusConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-15
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
    public Optional<SurveyQuestionResponseDTO> saveSurveyResponse(SurveyQuestionResponseDTO surveyQuestionResponseDTO) throws Exception {
        Optional<SurveyQuestionResponseDTO> optionalSurveyQuestionResponseDTO;
        try {
            optionalSurveyQuestionResponseDTO = surveyQuestionsDAO.saveSurveyResponse(surveyQuestionResponseDTO);

            String resMessage = String.format("Response of the user: %s for the Survey %s has been added to the database",
                    optionalSurveyQuestionResponseDTO.get().getSurveyId(),
                    optionalSurveyQuestionResponseDTO.get().getUserId());
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in adding Response of the user: %s for the Survey %s in the database",
                    surveyQuestionResponseDTO.getSurveyId(),
                    surveyQuestionResponseDTO.getUserId());
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return optionalSurveyQuestionResponseDTO;
    }
}
