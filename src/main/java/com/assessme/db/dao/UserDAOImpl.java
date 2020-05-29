package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private DBConnectionBuilder dbConnectionBuilder;

    private Optional<Connection> connection;

    // UserDAO method for retrieving user using email
    @Override
    public Optional<User> getUserByEmail(String email) throws Exception {

        Optional<User> user = Optional.empty();

        // SQL query for fetching the user record based on the email
        String selectQuery = "SELECT * FROM user WHERE email =" +  email;



        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            Statement statement = connection.get().createStatement();

            if ((!email.isEmpty() && email != null)) {

                ResultSet resultSet = statement.executeQuery(selectQuery);

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("User: %s is not found in the database", email));
                    throw new Exception(String.format("User: %s is not found in the database", email));
                }

                logger.info(String.format("User data retrieved successfully"));
                while (resultSet.next()) {

                    user = Optional.of(new User());

                    user.get().setUserId(resultSet.getInt("user_id"));
                    user.get().setBannerId(resultSet.getString("banner_id"));
                    user.get().setFirstName(resultSet.getString("first_name"));
                    user.get().setLastName(resultSet.getString("last_name"));
                    user.get().setEmail(resultSet.getString("email"));
                    user.get().setActive(resultSet.getBoolean("isActive"));


                }
                String successString = String.format("User with email: %s retrieved successfully.", email);
                logger.info(successString);

            } else
                throw new Exception(String.format("User: %s record is not found in the Database.", email));

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
        String selectUserQuery = "SELECT * FROM user";

        List<User> userList = new ArrayList<>();

        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            PreparedStatement preparedStatement = connection.get().prepareStatement("SELECT * FROM user");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                // Instantiating new user
                User user = new User();

                //Setting the attributes
                user.setUserId(resultSet.getInt("user_id"));
                user.setBannerId(resultSet.getString("banner_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setActive(resultSet.getBoolean("isActive"));

                // Adding user to the list
                userList.add(user);
            }

            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());

            logger.info(String.format("User list retrieved from the database: %s", userList));

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return userList;
    }

    @Override
    public Boolean addUser(User user) throws Exception {
        return null;
    }

    @Override
    public Optional<User> updateUser(User user) throws Exception {
        return Optional.empty();
    }
}
