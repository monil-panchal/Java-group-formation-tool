package com.assessme.controller;

import com.assessme.model.ResponseDTO;
import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import com.assessme.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    UserServiceImpl userService;

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
            e.printStackTrace();
            logger.error(e.getMessage());

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
            Optional<User> user = userService.getUserFromEmail(email);
            String resMessage = String.format("User has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, user);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving the user from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }


    // API endpoint method for fetching user using emailId
    @GetMapping("/roles")
    public ResponseEntity<ResponseDTO> getUserWithRolesFromEmail(@RequestParam("email") String email) throws Exception {

        logger.info("Calling API for user retrieval using email.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<UserRoleDTO>> responseDTO = null;

        try {
            Optional<UserRoleDTO> user = userService.getUserWithRolesFromEmail(email);
            String resMessage = String.format("User has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, user);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in retrieving the user from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }

    // API endpoint method for fetching user using emailId
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ResponseDTO> createUser(@RequestBody User user) throws Exception {

        logger.info("request:"+ user);

        logger.info("Calling API for creating a new user.");
        HttpStatus httpStatus = null;
        ResponseDTO<User> responseDTO = null;

        try {

            Optional<User> newUser = userService.addUser(user, null);
            String resMessage = String.format("User with email: %s has been successfully.", user.getEmail());

            responseDTO = new ResponseDTO(true, resMessage, null, newUser);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in creating a user in the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }
}
