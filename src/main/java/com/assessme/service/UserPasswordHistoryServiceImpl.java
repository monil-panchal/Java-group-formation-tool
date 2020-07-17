package com.assessme.service;

import com.assessme.db.dao.UserPasswordHistoryDAO;
import com.assessme.db.dao.UserPasswordHistoryDAOImpl;
import com.assessme.model.UserPasswordHistory;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: monil Created on: 2020-06-16
 */
@Service
public class UserPasswordHistoryServiceImpl implements UserPasswordHistoryService {

    private static UserPasswordHistoryServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(UserPasswordHistoryServiceImpl.class);
    private final UserPasswordHistoryDAO userPasswordHistoryDAO;

    public UserPasswordHistoryServiceImpl() {
        this.userPasswordHistoryDAO = UserPasswordHistoryDAOImpl.getInstance();
    }

    public static UserPasswordHistoryServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserPasswordHistoryServiceImpl();
        }
        return instance;
    }

    @Override
    public List<UserPasswordHistory> getUserPasswordHistory(Long userId, Integer lastPasswords) {
        List<UserPasswordHistory> userPasswordHistoryList = null;
        try {
            if (userId == null || lastPasswords == null) {
                throw new Exception("UserId or password cannot be null");
            }

            userPasswordHistoryList = userPasswordHistoryDAO
                .getUserPasswordHistory(userId, lastPasswords);

            if (userPasswordHistoryList == null || userPasswordHistoryList.isEmpty()) {
                String resMessage = String.format("User password history is empty");
                logger.info(resMessage);
            } else {
                String resMessage = String.format("User password history exists");
            }

        } catch (Exception e) {
            String errMessage = String.format("Error retrieving the password history record.");
            logger.error(errMessage);
            e.printStackTrace();
        }
        return userPasswordHistoryList;
    }

    @Override
    public Optional<UserPasswordHistory> addUserPasswordRecord(
        UserPasswordHistory userPasswordHistory) throws Exception {

        try {
            if (userPasswordHistory == null
                || userPasswordHistory.getUserId() == null
                || userPasswordHistory.getPassword() == null) {
                throw new Exception("UserId or password cannot be null");
            }

            Timestamp passwordModifiedOn = new Timestamp(
                Calendar.getInstance().getTime().getTime());
            userPasswordHistory.setModifiedOn(passwordModifiedOn);

            Optional<UserPasswordHistory> newUserPasswordUpdateRecord = userPasswordHistoryDAO
                .addPasswordModificationRecord(userPasswordHistory);

            if (newUserPasswordUpdateRecord.isPresent()) {
                String resMessage = String
                    .format("Password record for the user: %s has been saved in the database at %s",
                        userPasswordHistory.getUserId(), passwordModifiedOn.toString());
                logger.info(resMessage);
                return newUserPasswordUpdateRecord;
            } else {
                throw new Exception(String
                    .format("Error in creating a user password record for the user: %s",
                        userPasswordHistory.getUserId()));
            }

        } catch (Exception e) {
            String errMessage = String.format(
                "Error in saving the password update record the role to the user in the database.");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
    }
}

