package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.UserToken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-06-04
 */

public class UserTokenDAOImpl implements UserTokenDAO {

    private static UserTokenDAOImpl instance;
    final String getUserToken_selectQuery = "SELECT * FROM user_token u WHERE user_id = ?";
    final String insertUserTokenSQLQuery = "REPLACE INTO user_token values (?,?)";
    private final Logger logger = LoggerFactory.getLogger(UserTokenDAOImpl.class);
    private final ConnectionManager connectionManager;

    public UserTokenDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    public static UserTokenDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserTokenDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<UserToken> getUserToken(Long userId) throws Exception {

        Optional<UserToken> userToken = Optional.empty();

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement statement = connection.prepareStatement(getUserToken_selectQuery)
        ) {
            if ((userId != null)) {
                statement.setLong(1, userId);
                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.isBeforeFirst()) {
                    logger.error("No user token found in the database");
                    return userToken;

                }
                while (resultSet.next()) {
                    userToken = Optional.of(new UserToken());
                    userToken.get().setToken(resultSet.getString("token"));
                    userToken.get().setUserId(resultSet.getLong("user_id"));

                }
                String successString = String.format("User token retrieved successfully.");
                logger.info(successString);


            } else {
                throw new Exception("UserId cannot be null");
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        }

        return userToken;
    }


    @Override
    public Optional<UserToken> addUserToken(UserToken userToken) throws Exception {
        Optional<UserToken> newToken;
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(insertUserTokenSQLQuery, Statement.RETURN_GENERATED_KEYS)

        ) {
            preparedStatement.setLong(1, userToken.getUserId());
            preparedStatement.setString(2, userToken.getToken());

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                String successString = String
                        .format("User token with id: %s has been successfully inserted in the DB",
                                userToken.getUserId());
                logger.info(successString);

            } else {
                String failureString = String
                        .format("Failed to insert the user with id: %s record in the DB",
                                userToken.getUserId());
                logger.error(failureString);
                throw new Exception(failureString);
            }
            newToken = Optional.of(userToken);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return newToken;
    }
}
