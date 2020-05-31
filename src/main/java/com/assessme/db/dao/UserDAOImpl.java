package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    static UserDAO instance;
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    private DBConnectionBuilder dbConnectionBuilder;

    private Optional<Connection> connection;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    // UserDAO method for retrieving user using email
    @Override
    public Optional<User> getUserByEmail(String email) throws Exception {
        email = '\'' + email + '\'';

        Optional<User> user = Optional.empty();

        // SQL query for fetching the user record based on the email
        String selectQuery = "SELECT u.user_id, u.banner_id, u.first_name, u.last_name, u.email, u.isActive FROM user AS u WHERE email =" + email;

        try {
            if ((!email.isEmpty() && email != null)) {

                // Getting the DB connection
                connection = dbConnectionBuilder.createDBConnection();

                // Preparing the statement
                Statement statement = connection.get().createStatement();

                ResultSet resultSet = statement.executeQuery(selectQuery);

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("No users found in the database"));
                    throw new Exception(String.format("No users found in the database"));
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

                //Closing the connection
                dbConnectionBuilder.closeConnection(connection.get());
            } else
                throw new Exception(String.format("User email id cannot be null"));

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return user;
    }


    @Override
    public List<User> getAllUser() throws SQLException, ClassNotFoundException {

        // SQL query for fetching the all user
        String selectUserQuery = "SELECT u.banner_id, u.first_name, u.last_name, u.email, u.isActive FROM user AS u";

        List<User> userList = new ArrayList<>();

        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            PreparedStatement preparedStatement = connection.get().prepareStatement(selectUserQuery);

            ResultSet resultSet = preparedStatement.executeQuery();
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

            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        //Closing the connection
        dbConnectionBuilder.closeConnection(connection.get());
        return userList;
    }


    @Override
    public Optional<User> updateUser(User user) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<UserRoleDTO> getUserWithRolesFromEmail(String email) throws Exception {
        Optional<UserRoleDTO> user = Optional.empty();
        email = '\'' + email + '\'';


        // SQL query for fetching the user record with role_name based on the email.
        String selectQuery = "SELECT u.banner_id, u.first_name, u.last_name, u.email, u.isActive, u.password, r.role_name FROM user AS u" +
                " INNER JOIN user_role AS ur " +
                " ON u.user_id = ur.user_id " +
                " INNER JOIN role AS r " +
                " ON ur.role_id = r.role_id " +
                " WHERE u.email =" + email;

        try {
            if ((!email.isEmpty() && email != null)) {

                // Getting the DB connection
                connection = dbConnectionBuilder.createDBConnection();

                // Preparing the statement
                Statement statement = connection.get().createStatement();

                ResultSet resultSet = statement.executeQuery(selectQuery);

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

                //Closing the connection
                dbConnectionBuilder.closeConnection(connection.get());

            } else
                throw new Exception(String.format("User email id cannot be null"));

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    @Override
    public Optional<User> addUser(User user) throws Exception {

        Optional<User> newUser = Optional.empty();

        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Insert user record
            // SQL query for inserting user record
            String insertUserSQLQuery = "INSERT INTO user(banner_id, first_name, last_name, email, password, isActive)  values (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.get().prepareStatement(insertUserSQLQuery, Statement.RETURN_GENERATED_KEYS);

            //Setting the query params
            preparedStatement.setString(1, user.getBannerId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setBoolean(6, user.getActive());

            // Executing the query to store the user record
            int row = preparedStatement.executeUpdate();

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String.format("User record with email: %s has been successfully inserted in the DB", user.getEmail());
                logger.info(successString);

            } else {
                String failureString = String.format("Failed to insert the user with email: %s record in the DB", user.getEmail());
                logger.error(failureString);
                throw new Exception(failureString);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getLong(1));
                    newUser = Optional.of(user);
                } else {
                    throw new SQLException("Creation of user failed. Cannot obtain user_id.");
                }
            }
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());
            return newUser;

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}