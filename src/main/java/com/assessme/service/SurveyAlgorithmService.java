package com.assessme.service;

import com.assessme.model.SurveyQuestionsDTO;
import java.util.List;
import java.util.Optional;

/**
 * @author Darshan Kathiriya
 * @created 15-July-2020 4:02 PM
 */
public class SurveyAlgorithmService {
  long surveyId = 24;
  QuestionService questionService;
  UserService userService;
  SurveyQuestionsServiceImpl surveyQuestionsService;

  public SurveyAlgorithmService() {
    questionService = QuestionServiceImpl.getInstance();
    surveyQuestionsService = SurveyQuestionsServiceImpl.getInstance();
  }

  public void prepareVector() {
    try {
      Optional<SurveyQuestionsDTO> surveyQuestions = surveyQuestionsService
          .getSurveyQuestions(surveyId);
      List<Long> questionList = surveyQuestions.get().getQuestionList();
      for(long questionId : questionList){
//        questionService.getQuestionById();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
