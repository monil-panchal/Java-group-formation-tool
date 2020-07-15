package com.assessme.service;

import com.assessme.db.dao.SurveyQuestionsDAOImpl;
import com.assessme.model.Survey;
import com.assessme.model.SurveyQuestions;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.util.SurveyStatusConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-07-15
 */

@Service
public class SurveyQuestionsServiceImpl implements SurveyQuestionsService {

    private static SurveyQuestionsServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(SurveyQuestionsServiceImpl.class);

    private final SurveyQuestionsDAOImpl surveyQuestionsDAO;
    private final SurveyService surveyService;

    public SurveyQuestionsServiceImpl() {
        this.surveyQuestionsDAO = SurveyQuestionsDAOImpl.getInstance();
        this.surveyService = SurveyServiceImpl.getInstance();
    }

    public static SurveyQuestionsServiceImpl getInstance() {
        if (instance == null) {
            instance = new SurveyQuestionsServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<SurveyQuestionsDTO> addQuestionsToSurvey(SurveyQuestionsDTO surveyQuestionsDTO) throws Exception {
        Optional<SurveyQuestionsDTO> questionsDTO;
        try {

            Optional<Survey> survey = surveyService.getSurvey(surveyQuestionsDTO.getSurveyId());

            if(survey.isEmpty()){
                String resMessage = String.format("surveyId should not be null or empty while adding the questions.");
                throw new Exception(resMessage);
            }

            if(survey.get().getStatus().equalsIgnoreCase(SurveyStatusConstant.PUBLISHED.getSurveyStatus())){
                String resMessage = String.format("Survey: %s is already published and hence cannot add questions to it. "
                        , survey.get().getSurveyName());
                throw new Exception(resMessage);

            }

            questionsDTO = surveyQuestionsDAO.addSurveyQuestions(surveyQuestionsDTO);
            String resMessage = String.format("Questions for the Survey %s has been added to the database", surveyQuestionsDTO.getSurveyId());
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in adding questions to the survey: %s for in the database", surveyQuestionsDTO.getSurveyId());
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return questionsDTO;
    }

    @Override
    public Optional<SurveyQuestionsDTO> getSurveyQuestions(Long surveyId) throws Exception {
        List<SurveyQuestions> surveyQuestions = null;
        Optional<SurveyQuestionsDTO> questionsDTO = Optional.empty();
        try {
            if (surveyId == null) {
                throw new Exception("SurveyId cannot be null for fetching questions list");
            }
            surveyQuestions = surveyQuestionsDAO.getSurveyQuestions(surveyId);
            questionsDTO = Optional.of(new SurveyQuestionsDTO());
            questionsDTO.get().setSurveyId(surveyId);

            List<Long> questionIdList = new ArrayList<>();
            for(SurveyQuestions questions : surveyQuestions){
                questionIdList.add(questions.getQuestionId());
            }

            questionsDTO.get().setQuestionList(questionIdList);
            String resMessage = String.format("Survey question list has been retrieved from the database");
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the Survey question list from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return questionsDTO;
    }
}
