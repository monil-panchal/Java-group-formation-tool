package com.assessme.controller;

import com.assessme.model.ResponseDTO;
import com.assessme.model.User;
import com.assessme.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-05-28
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    // API endpoint method for fetching all users
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getUsers() throws Exception {

        logger.info("Calling API for user retrieval.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<User>> responseDTO = null;

        try {
            Optional<List<User>> userList = userService.getUserList();
            String resMessage = String.format("User list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, userList);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the user list from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(responseDTO, httpStatus);
    }

    // API endpoint method for fetching user using emailId
    @GetMapping()
    public ResponseEntity<ResponseDTO> getUserFromEmail(@RequestParam("email") String email) throws Exception {

        logger.info("Calling API for user retrieval using email.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<User>> responseDTO = null;

        try {
            Optional<User> user = userService.getUserWithEmail(email);
            String resMessage = String.format("User has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, user);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the user from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }
}
