package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.UserPasswordHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-06-16
 */
@Repository
public class UserPasswordHistoryDAOImpl implements UserPasswordHistoryDAO {

    private final Logger logger = LoggerFactory.getLogger(UserPasswordHistoryDAOImpl.class);

    private final ConnectionManager connectionManager;

    public UserPasswordHistoryDAOImpl() {
        connectionManager = new ConnectionManager();
    }


    @Override
    public Optional<UserPasswordHistory> addPasswordModificationRecord(UserPasswordHistory userPasswordHistory) throws Exception {
        Optional<UserPasswordHistory> newPasswordModificationRecord = Optional.empty();
        String insertPasswordModificationQuery =
                "INSERT INTO user_password_history values (?,?,?)";
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(insertPasswordModificationQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            //Setting the query params
            preparedStatement.setLong(1, userPasswordHistory.getUserId());
            preparedStatement.setString(2, userPasswordHistory.getPassword());
            preparedStatement.setTimestamp(3, userPasswordHistory.getModifiedOn());

            // Executing the query to store the user record
            int row = preparedStatement.executeUpdate();

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String
                        .format("User password change record record for the user: %s has been successfully inserted in the DB",
                                userPasswordHistory.getUserId());
                logger.info(successString);

            } else {
                String failureString = String
                        .format("Failed to insert password change record record for the user: %s in the DB", userPasswordHistory.getUserId());
                logger.error(failureString);
                throw new Exception(failureString);
            }
            newPasswordModificationRecord = Optional.of(userPasswordHistory);

            return newPasswordModificationRecord;

        } catch (Exception e) {
            // Getting the DB connection
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public List<UserPasswordHistory> getUserPasswordHistory(Long userId, Integer lastPasswords) throws Exception {
        return null;
    }
}
