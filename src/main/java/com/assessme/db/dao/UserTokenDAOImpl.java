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
import org.springframework.stereotype.Repository;

/**
 * @author: monil Created on: 2020-06-04
 */

/**
 * The {@link UserTokenDAO} implementation class for performing CRUD operations for the user_token
 * table of the database.
 */
@Repository
public class UserTokenDAOImpl implements UserTokenDAO {

  private final Logger logger = LoggerFactory.getLogger(UserTokenDAOImpl.class);

  private final ConnectionManager connectionManager;

  public UserTokenDAOImpl() {
    connectionManager = new ConnectionManager();
  }

  @Override
  public Optional<UserToken> getUserToken(Long userId) throws Exception {

    Optional<UserToken> userToken = Optional.empty();

    // SQL query for fetching the user_token
    String selectQuery = "SELECT * FROM user_token u WHERE user_id =" + userId;

    try (
        Connection connection = connectionManager.getDBConnection().get();
        Statement statement = connection.createStatement()
    ) {
      if ((userId != null)) {

        ResultSet resultSet = statement.executeQuery(selectQuery);

        if (!resultSet.isBeforeFirst()) {
          logger.error("No user token found in the database");
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
    String insertUserTokenSQLQuery = "REPLACE INTO user_token values (?,?)";

    try (
        Connection connection = connectionManager.getDBConnection().get();
        PreparedStatement preparedStatement = connection
            .prepareStatement(insertUserTokenSQLQuery, Statement.RETURN_GENERATED_KEYS)

    ) {

      //Setting the query params
      preparedStatement.setLong(1, userToken.getUserId());
      preparedStatement.setString(2, userToken.getToken());

      // Executing the query to store the user record
      int row = preparedStatement.executeUpdate();

      // check if the record was inserted successfully
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
      logger.error(e.getMessage());
      e.printStackTrace();
      throw e;
    }
    return newToken;
  }
}