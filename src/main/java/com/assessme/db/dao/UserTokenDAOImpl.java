package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-06-04
 */

/**
 * The {@link UserTokenDAO} implementation class for performing CRUD operations for the user_token table of the database.
 */
@Repository
public class UserTokenDAOImpl implements UserTokenDAO {

    private Logger logger = LoggerFactory.getLogger(UserTokenDAOImpl.class);

    private DBConnectionBuilder dbConnectionBuilder;
    private Optional<Connection> connection;

    public UserTokenDAOImpl(DBConnectionBuilder dbConnectionBuilder) {
        this.dbConnectionBuilder = dbConnectionBuilder;
    }

    @Override
    public Optional<UserToken> getUserToken(Long userId) throws Exception {

        Optional<UserToken> userToken = Optional.empty();

        // SQL query for fetching the user_token
        String selectQuery = "SELECT * FROM user_token u WHERE user_id =" + userId;

        try {
            if ((userId != null)) {

                // Getting the DB connection
                connection = dbConnectionBuilder.createDBConnection();

                // Preparing the statement
                Statement statement = connection.get().createStatement();

                ResultSet resultSet = statement.executeQuery(selectQuery);

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("No user token found in the database"));
                   return userToken;

                }

                // Iterating through the rows and constructing user object
                while (resultSet.next()) {
                    userToken = Optional.of(new UserToken());
                    userToken.get().setToken(resultSet.getString("token"));
                    userToken.get().setUserId(resultSet.getLong("user_id"));

                }
                String successString = String.format("User token retrieved successfully.");
                logger.info(successString);


            } else
                throw new Exception(String.format("UserId cannot be null"));

        } catch (Exception e) {
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
        //Closing the connection
        dbConnectionBuilder.closeConnection(connection.get());
        return userToken;
    }


    @Override
    public Optional<UserToken> addUserToken(UserToken userToken) throws Exception {
        Optional<UserToken> newToken = Optional.empty();

        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Insert user token record
            // SQL query for inserting user_token record
            String insertUserTokenSQLQuery = "INSERT INTO user_token values (?,?)";
            PreparedStatement preparedStatement = connection.get().prepareStatement(insertUserTokenSQLQuery, Statement.RETURN_GENERATED_KEYS);

            //Setting the query params
            preparedStatement.setLong(1, userToken.getUserId());
            preparedStatement.setString(2, userToken.getToken());

            // Executing the query to store the user record
            int row = preparedStatement.executeUpdate();

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String.format("User token with id: %s has been successfully inserted in the DB", userToken.getUserId());
                logger.info(successString);

            } else {
                String failureString = String.format("Failed to insert the user with id: %s record in the DB", userToken.getUserId());
                logger.error(failureString);
                throw new Exception(failureString);
            }

//                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        userToken.setUserId(generatedKeys.getLong(1));
//                        newToken = Optional.of(userToken);
//                    } else {
//                        throw new SQLException("Creation of user token failed. Cannot obtain user_id.");
//                    }
//                }
            newToken = Optional.of(userToken);

        } catch (Exception e) {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        //Closing the connection
        dbConnectionBuilder.closeConnection(connection.get());
        return newToken;
    }
}