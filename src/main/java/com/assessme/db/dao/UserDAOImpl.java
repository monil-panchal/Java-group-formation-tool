package com.assessme.db.dao;

import com.assessme.db.CallStoredProcedure;
import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

/**
 * @author: monil
 * Created on: 2020-05-29
 */

/**
 * The {@link UserDAO} implementation class for performing CRUD operations for the user table of the database.
 */
@Repository
public class UserDAOImpl implements UserDAO {
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private final ConnectionManager connectionManager;

    public UserDAOImpl() {
        connectionManager = new ConnectionManager();
    }


    // UserDAO method for retrieving user using email
    @Override
    public Optional<User> getUserByEmail(String email) throws Exception {
        CallStoredProcedure procedure = null;

        Optional<User> user = Optional.empty();

        try {
            if ((!email.isEmpty() && email != null)) {
                //calling procedure
                procedure = new CallStoredProcedure( "spFindUserByEmail(?)");

                //setting query parameters
                procedure.setParameter(1, email);

                //obtaining result set
                ResultSet resultSet = procedure.getResultSet();

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("No user found in the database"));
                    throw new Exception(String.format("No user found in the database"));
                }

                logger.info(String.format("User data retrieved successfully"));

                // Iterating through the rows and constructing user object
                while (resultSet.next()) {
                    user = Optional.of(new User());
                    user.get().setUserId(resultSet.getLong("user_id"));
                    user.get().setBannerId(resultSet.getString("banner_id"));
                    user.get().setFirstName(resultSet.getString("first_name"));
                    user.get().setLastName(resultSet.getString("last_name"));
                    user.get().setEmail(resultSet.getString("email"));
                    user.get().setActive(resultSet.getBoolean("isActive"));

                }
                String successString = String.format("User list retrieved successfully.");
                logger.info(successString);


            } else
                throw new Exception(String.format("User email id cannot be null"));

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
        return user;
    }


    @Override
    public List<User> getAllUser() throws SQLException, ClassNotFoundException {

        CallStoredProcedure procedure = null;

        List<User> userList = new ArrayList<>();

        try {
            // Calling procedure
            procedure = new CallStoredProcedure( "spFindAllUsers()");

            // Obtaining result set
            ResultSet resultSet = procedure.getResultSet();

            while (resultSet.next()) {

                // Instantiating new user
                User user = new User();

                //Setting the attributes
                user.setBannerId(resultSet.getString("banner_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setActive(resultSet.getBoolean("isActive"));

                // Adding user to the list
                userList.add(user);
            }
            logger.info(String.format("User list retrieved from the database: %s", userList));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
        return userList;
    }

    @Override
    public Optional<UserRoleDTO> getUserWithRolesFromEmail(String email) throws Exception {
        Optional<UserRoleDTO> user = Optional.empty();
        CallStoredProcedure procedure = null;

        try {
            if ((!email.isEmpty() && email != null)) {

                // Calling procedure
                procedure = new CallStoredProcedure("spGetUserWithRolesFromEmail(?)");

                // Setting query parameters
                procedure.setParameter(1, email);

                // Obtaining result set
                ResultSet resultSet = procedure.getResultSet();

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("User: %s is not found in the database", email));
                    throw new Exception(String.format("User: %s is not found in the database", email));
                }

                logger.info(String.format("User data retrieved successfully"));

                user = Optional.of(new UserRoleDTO());
                Set<String> userRoles = new HashSet();

                // Getting the first column and building the UserRoleDTO object
                resultSet.next();

                user.get().setBannerId(resultSet.getString("banner_id"));
                user.get().setFirstName(resultSet.getString("first_name"));
                user.get().setLastName(resultSet.getString("last_name"));
                user.get().setEmail(resultSet.getString("email"));
                user.get().setActive(resultSet.getBoolean("isActive"));
                user.get().setPassword(resultSet.getString("password"));
                userRoles.add(resultSet.getString("role_name"));

                //Getting all the roles for the user and adding to Set
                while (resultSet.next()) {
                    userRoles.add(resultSet.getString("role_name"));
                }

                user.get().setUserRoles(userRoles);

                String successString = String.format("User with email: %s retrieved successfully.", email);
                logger.info(successString);

            } else {
                throw new Exception(String.format("User email id cannot be null"));
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
        return user;
    }

    @Override
    public Optional<User> addUser(User user) throws Exception {

        Optional<User> newUser = Optional.empty();
        CallStoredProcedure procedure = null;

        try {
            // Getting the DB connection
//            connection = dbConnectionBuilder.createDBConnection();

            // Insert user record
            // SQL query for inserting user record
//            String insertUserSQLQuery = "INSERT INTO user(banner_id, first_name, last_name, email, password, isActive)  values (?,?,?,?,?,?)";
//            PreparedStatement preparedStatement = connection.get().prepareStatement(insertUserSQLQuery, Statement.RETURN_GENERATED_KEYS);
            // Calling procedure
            procedure = new CallStoredProcedure("spAddUser(?,?,?,?,?)");
            //Setting the query params
            procedure.setParameter(1, user.getBannerId());
            procedure.setParameter(2, user.getFirstName());
            procedure.setParameter(3, user.getLastName());
            procedure.setParameter(4, user.getEmail());
            procedure.setParameter(5, user.getPassword());
            procedure.setParameter(6, user.getActive());

            // Executing the query to store the user record
            int row = procedure.executeUpdate();

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String.format("User record with email: %s has been successfully inserted in the DB", user.getEmail());
                logger.info(successString);

            } else {
                String failureString = String.format("Failed to insert the user with email: %s record in the DB", user.getEmail());
                logger.error(failureString);
                throw new Exception(failureString);
            }

            try (ResultSet generatedKeys = procedure.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getLong(1));
                    newUser = Optional.of(user);
                } else {
                    throw new SQLException("Creation of user failed. Cannot obtain user_id.");
                }
            }
            return newUser;

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }  finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
    }

    @Override
    public Optional<User> updateUserPassword(User user) throws Exception {

        Optional<User> updatedUserObj = Optional.empty();
        CallStoredProcedure procedure = null;
        try {
            // Calling stored procedure
            procedure = new CallStoredProcedure("spUpdatePasswordWithEmail(?,?)");

            //Setting the query params
            procedure.setParameter(1, user.getPassword());
            procedure.setParameter(2, user.getEmail());

            // Executing the query to store the user record
            int row = procedure.executeUpdate();

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String.format("User record with email: %s has been successfully updated in the DB", user.getEmail());
                logger.info(successString);

            } else {
                String failureString = String.format("Failed to update the user with email: %s record in the DB", user.getEmail());
                logger.error(failureString);
                throw new Exception(failureString);
            }
            return updatedUserObj;

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
    }

    @Override
    public List<User> getUserNotAssignedForCourse(long courseId, int roleId) throws Exception{
        List<User> userList = new ArrayList<>();
        CallStoredProcedure procedure = null;
        try{
            procedure = new CallStoredProcedure("getUserNotAssignedForCourse(?,?)");
            procedure.setParameter(1, courseId);
            procedure.setParameter(2, roleId);
            ResultSet resultSet = procedure.getResultSet();
            while (resultSet.next()) {
                // Instantiating new user
                User user = new User();
                //Setting the attributes
                user.setBannerId(resultSet.getString("banner_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setActive(resultSet.getBoolean("isActive"));
                // Adding user to the list
                userList.add(user);
            }
            logger.info(String.format("User list retrieved from the database: %s", userList));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
        return userList;
    }

}