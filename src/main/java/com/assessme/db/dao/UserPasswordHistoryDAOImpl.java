package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.UserPasswordHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserPasswordHistoryDAOImpl implements UserPasswordHistoryDAO {

    private static UserPasswordHistoryDAOImpl instance;
    final String insertPasswordModificationQuery =
        "INSERT INTO user_password_history values (?,?,?)";
    final String selectUserQuery = "SELECT * FROM user_password_history WHERE user_id = ? "
        + "ORDER BY modified_on DESC LIMIT ?";
    private final Logger logger = LoggerFactory.getLogger(UserPasswordHistoryDAOImpl.class);
    private final ConnectionManager connectionManager;

    public UserPasswordHistoryDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    public static UserPasswordHistoryDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserPasswordHistoryDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<UserPasswordHistory> addPasswordModificationRecord(
        UserPasswordHistory userPasswordHistory) throws Exception {
        Optional<UserPasswordHistory> newPasswordModificationRecord = Optional.empty();

        try (
            Connection connection = connectionManager.getDBConnection().get();
            PreparedStatement preparedStatement = connection
                .prepareStatement(insertPasswordModificationQuery, Statement.RETURN_GENERATED_KEYS)
        ) {

            preparedStatement.setLong(1, userPasswordHistory.getUserId());
            preparedStatement.setString(2, userPasswordHistory.getPassword());
            preparedStatement.setTimestamp(3, userPasswordHistory.getModifiedOn());

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                String successString = String
                    .format(
                        "User password change record record for the user: %s has been successfully inserted in the DB",
                        userPasswordHistory.getUserId());
                logger.info(successString);

            } else {
                String failureString = String
                    .format(
                        "Failed to insert password change record record for the user: %s in the DB",
                        userPasswordHistory.getUserId());
                logger.error(failureString);
                throw new Exception(failureString);
            }
            newPasswordModificationRecord = Optional.of(userPasswordHistory);

            return newPasswordModificationRecord;

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public List<UserPasswordHistory> getUserPasswordHistory(Long userId, Integer lastPasswords)
        throws Exception {
        List<UserPasswordHistory> userPasswordHistoryList = new ArrayList<>();

        try (
            Connection connection = connectionManager.getDBConnection().get();
            PreparedStatement preparedStatement = connection.prepareStatement(selectUserQuery)
        ) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, lastPasswords);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                UserPasswordHistory userPasswordHistory = new UserPasswordHistory();

                userPasswordHistory.setUserId(resultSet.getLong("user_id"));
                userPasswordHistory.setPassword(resultSet.getString("password"));
                userPasswordHistory.setModifiedOn(resultSet.getTimestamp("modified_on"));

                userPasswordHistoryList.add(userPasswordHistory);
            }
            logger.info(String
                .format("User password list retrieved from the database: %s",
                    userPasswordHistoryList));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return userPasswordHistoryList;
    }
}
