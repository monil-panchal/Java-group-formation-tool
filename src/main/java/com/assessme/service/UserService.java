package com.assessme.service;

import com.assessme.db.dao.UserDAOImpl;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.model.UserRole;
import com.assessme.model.UserRoleDTO;
import com.assessme.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;


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
    public Optional<User> getUserFromEmail(String email) throws Exception {

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


    /**
     * Service method for retrieving user based on email
     */
    public Optional<UserRoleDTO> getUserWithRolesFromEmail(String email) throws Exception {

        Optional<UserRoleDTO> user;
        try {
            user = userDAOImpl.getUserWithRolesFromEmail(email);
            String resMessage = String.format("User with email: %s has been retrieved from the database : %s", email, user.get());
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the user from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return user;
    }


    /**
     * Service method for inserting user record
     */
    public Optional<User> addUser(User user, String userRole) throws Exception {

        logger.info("user: "+ user);

        Optional<User> newUser = Optional.empty();
        Optional<Role> role = Optional.empty();
        Optional<UserRole> newUserRole = Optional.empty();
        try {

            //TODO
            //Step -1
            //Validate the User object

            if(user.getActive() == null){
                user.setActive(true);
            }

            //Step-2
            // Check for password
            if (user.getPassword() == null || user.getPassword().isBlank() || user.getPassword().isEmpty()) {
                // generate new Password
                String password = user.getBannerId() + "_" + user.getLastName();
                logger.info(String.format("User: %s default password is: %s", user.getEmail(), password));
                user.setPassword(password);

                //TODO
                //secure password
            }

            // Step-3 Insert user record in the user table
            newUser = userDAOImpl.addUser(user);
            if (newUser.isPresent()) {
                //  newUser = Optional.of(user);
                String resMessage = String.format("User with email: %s has been successfully added to the user table", user.getEmail());
                logger.info(resMessage);
            } else {
                throw new Exception(String.format("Error in creating a user with email: %s", user.getEmail()));
            }

            // Step-4 Get role_id from role table

            if (userRole == null || userRole.isBlank() || userRole.isBlank()) {
                userRole = AppConstant.DEFAULT_USER_ROLE_CREATE;
            }
            role = roleService.getRoleFromRoleName(userRole);

            if (role.isPresent()) {

                // Step-5 Update user_role table
                newUserRole = userRoleService.addUserRole(newUser.get().getUserId(), role.get().getRoleId());

            } else
                throw new Exception("Unable to fetch role id for the user from the role table.");

            if (newUser.isPresent()) {
                String resMessage = String.format("User: %s has been assigned with the role: %s in the system", user.getEmail(), userRole);
                logger.info(resMessage);
            } else
                throw new Exception(String.format("Unable to assign the role: %s to the user: %s", userRole, user.getEmail()));

        } catch (Exception e) {
            String errMessage = String.format("Error in adding the user to the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return newUser;
    }



}