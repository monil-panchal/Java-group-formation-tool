package com.assessme.service;

import com.assessme.db.dao.SurveyQuestionsDAO;
import com.assessme.db.dao.SurveyQuestionsDAOImpl;
import com.assessme.model.Question;
import com.assessme.model.QuestionDetailsDTO;
import com.assessme.model.QuestionType;
import com.assessme.model.Survey;
import com.assessme.model.SurveyQuestions;
import com.assessme.model.SurveyQuestionsDTO;
import com.assessme.model.SurveyQuestionsDetails;
import com.assessme.util.SurveyStatusConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: monil Created on: 2020-07-15
 */

@Service
public class SurveyQuestionsServiceImpl implements SurveyQuestionsService {

    private static SurveyQuestionsServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(SurveyQuestionsServiceImpl.class);

    private final SurveyQuestionsDAO surveyQuestionsDAO;
    private final SurveyService surveyService;
    private final QuestionService questionService;

    public SurveyQuestionsServiceImpl() {
        this.surveyQuestionsDAO = SurveyQuestionsDAOImpl.getInstance();
        this.surveyService = SurveyServiceImpl.getInstance();
        this.questionService = QuestionServiceImpl.getInstance();
    }

    public static SurveyQuestionsServiceImpl getInstance() {
        if (instance == null) {
            instance = new SurveyQuestionsServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<SurveyQuestionsDTO> addQuestionsToSurvey(SurveyQuestionsDTO surveyQuestionsDTO)
        throws Exception {
        Optional<SurveyQuestionsDTO> questionsDTO;
        try {

            Optional<Survey> survey = surveyService.getSurvey(surveyQuestionsDTO.getSurveyId());

            if (survey.isEmpty()) {
                String resMessage = String
                    .format("surveyId should not be null or empty while adding the questions.");
                throw new Exception(resMessage);
            }

            if (survey.get().getStatus()
                .equalsIgnoreCase(SurveyStatusConstant.PUBLISHED.getSurveyStatus())) {
                String resMessage = String
                    .format("Survey: %s is already published and hence cannot add questions to it. "
                        , survey.get().getSurveyName());
                throw new Exception(resMessage);

            }

            questionsDTO = surveyQuestionsDAO.addSurveyQuestions(surveyQuestionsDTO);
            String resMessage = String
                .format("Questions for the Survey %s has been added to the database",
                    surveyQuestionsDTO.getSurveyId());
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String
                .format("Error in adding questions to the survey: %s for in the database",
                    surveyQuestionsDTO.getSurveyId());
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
            for (SurveyQuestions questions : surveyQuestions) {
                questionIdList.add(questions.getQuestionId());
            }

            questionsDTO.get().setQuestionList(questionIdList);
            String resMessage = String
                .format("Survey question list has been retrieved from the database");
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String
                .format("Error in retrieving the Survey question list from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return questionsDTO;
    }

    @Override
    public Optional<SurveyQuestionsDetails> getSurveyQuestionsDetails(Long surveyId)
        throws Exception {

        Optional<SurveyQuestionsDTO> surveyQuestionsDTO;
        Optional<SurveyQuestionsDetails> surveyQuestionsDetails = Optional.empty();
        Optional<List<QuestionType>> questionTypeList;

        try {
            if (surveyId == null) {
                throw new Exception("SurveyId cannot be null for fetching questions list");
            }
            surveyQuestionsDTO = this.getSurveyQuestions(surveyId);

            if (surveyQuestionsDTO.isPresent()
                && surveyQuestionsDTO.get().getQuestionList().size() > 0) {
                logger.info(String.format("Question list retrieved for the survey: %s", surveyId));

                SurveyQuestionsDetails questionsDetails = new SurveyQuestionsDetails();
                List<Long> questionIdList = surveyQuestionsDTO.get().getQuestionList();
                List<QuestionDetailsDTO> questionDetailsDTOList = new ArrayList<>();

                questionTypeList = questionService.getAllQuestionType();

                Map<Integer, String> questionTypeMap = questionTypeList.get().stream()
                    .collect(Collectors
                        .toMap(QuestionType::getQuestionTypeID, QuestionType::getQuestionTypeText));

                for (Long questionId : questionIdList) {
                    Optional<Question> questionObj = questionService.getQuestionById(questionId);
                    if (questionObj.isPresent()) {
                        QuestionDetailsDTO questionDetailsDTO = new QuestionDetailsDTO
                            .Builder(questionObj.get().getQuestionId())
                            .hasQuestionText(questionObj.get().getQuestionText())
                            .hasQuestionTitle(questionObj.get().getQuestionTitle())
                            .hasQuestionTypeId(questionObj.get().getQuestionTypeId())
                            .hasQuestionTypeText(
                                questionTypeMap.get(questionObj.get().getQuestionTypeId()))
                            .hasOptions(Arrays.asList(questionObj.get().getOptionText()))
                            .build();

                        questionDetailsDTOList.add(questionDetailsDTO);

                    }
                }
                questionsDetails.setSurveyId(surveyId);
                questionsDetails.setQuestions(questionDetailsDTOList);

                surveyQuestionsDetails = Optional.of(questionsDetails);

                String resMessage = String
                    .format("Survey: %s with question details :%s successfully build.", surveyId,
                        surveyQuestionsDetails.get());
                logger.info(resMessage);
            } else {
                throw new Exception("Survey or its questions does not exists");
            }

        } catch (Exception e) {
            String errMessage = String
                .format("Survey: %s or its questions does not exists", surveyId);
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return surveyQuestionsDetails;
    }
}
