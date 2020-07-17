package com.assessme.controller;

import com.assessme.db.dao.UserRoleDAO;
import com.assessme.model.ResponseDTO;
import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import com.assessme.service.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: monil Created on: 2020-05-28
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = UserServiceImpl.getInstance();
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getUsers() throws Exception {

        logger.info("Calling API for user retrieval.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<User>> responseDTO = null;

        try {
            Optional<List<User>> userList = userServiceImpl.getUserList();
            String resMessage = String.format("User list has been retrieved from the database");
            responseDTO = new ResponseDTO(true, resMessage, null, userList);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String
                    .format("Error in retrieving the user list from the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(responseDTO, httpStatus);
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO> getUserFromEmail(@RequestParam("email") String email)
            throws Exception {

        logger.info("Calling API for user retrieval using email.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<User>> responseDTO = null;

        try {
            Optional<User> user = userServiceImpl.getUserFromEmail(email);
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

    @GetMapping("/roles")
    public ResponseEntity<ResponseDTO> getUserWithRolesFromEmail(
            @RequestParam("email") String email)
            throws Exception {

        logger.info("Calling API for user retrieval using email.");
        HttpStatus httpStatus = null;
        ResponseDTO<List<UserRoleDTO>> responseDTO = null;

        try {
            Optional<UserRoleDTO> user = userServiceImpl.getUserWithRolesFromEmail(email);
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

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> createUser(@RequestBody User user) throws Exception {

        logger.info("request:" + user);

        logger.info("Calling API for creating a new user.");
        HttpStatus httpStatus = null;
        ResponseDTO<User> responseDTO = null;

        try {

            Optional<User> newUser = userServiceImpl.addUser(user, null);
            String resMessage = String
                    .format("User with email: %s has been successfully.", user.getEmail());

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

    @PutMapping(path = "/roles", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateUserRole(@RequestBody UserRoleDTO user)
            throws Exception {

        logger.info("Calling API for updating the user role.");
        HttpStatus httpStatus = null;
        ResponseDTO<UserRoleDAO> responseDTO = null;

        try {

            Optional<UserRoleDTO> newUser = userServiceImpl
                    .updateUserRole(user,
                            user.getUserRoles().stream().collect(Collectors.toList()).get(0));
            String resMessage = String
                    .format("User with email: %s has been successfully assigned the role: %s .",
                            user.getEmail(),
                            user.getUserRoles().stream().collect(Collectors.toList()).get(0));

            responseDTO = new ResponseDTO(true, resMessage, null, newUser);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            String errMessage = String.format("Error in updating the user role in the database");
            responseDTO = new ResponseDTO(false, errMessage, e.getLocalizedMessage(), null);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(responseDTO, httpStatus);
    }
}
