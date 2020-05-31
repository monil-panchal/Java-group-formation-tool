package com.assessme.service;

import com.assessme.db.dao.UserDAOImpl;
import com.assessme.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-05-28
 */

/**
 * User service layer class for this application
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAOImpl userDAOImpl;

    /**
     * Service method for retrieving all users
     */
    public Optional<List<User>> getUserList() throws Exception {

        Optional<List<User>> userList = Optional.empty();
        try {
            userList = Optional.of(userDAOImpl.getAllUser());

            String resMessage = String.format("User list has been retrieved from the database");
            logger.info(resMessage);

        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the user list from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return userList;
    }

    /**
     * Service method for retrieving user based on email
     */
    public Optional<User> getUserWithEmail(String email) throws Exception {

        Optional<User> user;
        try {
            user = userDAOImpl.getUserByEmail(email);
            String resMessage = String.format("User with email: %s has been retrieved from the database", email);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the user from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return user;
    }
}