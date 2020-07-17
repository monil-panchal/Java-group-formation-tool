package com.assessme.controller;

import com.assessme.auth.CurrentUserService;
import com.assessme.model.Question;
import com.assessme.model.ResponseDTO;
import com.assessme.model.User;
import com.assessme.service.QuestionService;
import com.assessme.service.QuestionServiceImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 11:22 AM
 */
@Controller
public class QuestionManagerController {

    private final Logger logger = LoggerFactory.getLogger(QuestionManagerController.class);
    CurrentUserService currentUserService;
    QuestionService questionService;

    public QuestionManagerController() {
        this.currentUserService = CurrentUserService.getInstance();
        this.questionService = QuestionServiceImpl.getInstance();
    }

    @GetMapping("/question_manager")
    public ModelAndView getQuestionManagerPage() {
        ModelAndView mav = new ModelAndView("question_manager");
        try {
            User user = currentUserService.getAuthenticatedUser().get();
            logger.info(String.format("Getting Questions Manager for User %d", user.getUserId()));
            List<Question> questionsByUser = questionService.getQuestionsByUser(user).get();
            mav.addObject("questions", questionsByUser);
        } catch (Exception e) {
            logger.error("Error Getting Question Manager Page");
            mav.addObject("message", "Error Fetching Page");
        }
        return mav;
    }

    @PostMapping("/remove_question")
    public ResponseEntity<ResponseDTO> removeQuestion(@RequestParam long questionId) {
        HttpStatus httpStatus = null;
        ResponseDTO<Long> responseDTO = null;
        logger.info("removing question: " + questionId);
        try {
            questionService.removeQuestion(questionId);
            responseDTO = new ResponseDTO(true, null, null, null);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("Error Deleting Question");
            responseDTO = new ResponseDTO(false, null, null, null);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity(responseDTO, httpStatus);
    }

    @GetMapping("/question_editor")
    public ModelAndView getQuestionEditorPage() {
        ModelAndView mav = new ModelAndView("question_editor");
        try {
            long userId = currentUserService.getAuthenticatedUser().get().getUserId();
            logger.info(String.format("Getting Questions Editor for User %d", userId));
            mav.addObject("questionTypeList", questionService.getAllQuestionType().get());
        } catch (Exception e) {
            logger.error("Error Getting Question Editor Page");
            mav.addObject("message", "Error Fetching Page");
        }
        return mav;
    }

    @PostMapping("/add_question")
    public ResponseEntity<ResponseDTO> getQuestionEditorPage(@RequestParam int questionType,
        @RequestParam String questionText,
        @RequestParam String questionTitle,
        @RequestParam(required = false) String[] questionChoiceText,
        @RequestParam(required = false) int[] questionChoiceValue
    ) {
        HttpStatus httpStatus = null;
        ResponseDTO<Long> responseDTO = null;
        logger.info(String.format("%s, %d, %s", questionTitle, questionType, questionText));

        try {
            long userId = currentUserService.getAuthenticatedUser().get().getUserId();
            Question question = new Question();
            question.setUserId(userId);
            question.setQuestionText(questionText);
            question.setQuestionTitle(questionTitle);
            question.setQuestionTypeId(questionType);
            if (questionType == 2 || questionType == 3) {
                question.setOptionText(questionChoiceText);
                question.setOptionValue(questionChoiceValue);
            }
            questionService.addQuestion(question);
            responseDTO = new ResponseDTO(true, null, null, null);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("Error Getting Question Editor Page");
            responseDTO = new ResponseDTO(false, null, null, null);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity(responseDTO, httpStatus);
    }
}
