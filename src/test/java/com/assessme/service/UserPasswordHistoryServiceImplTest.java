package com.assessme.service;

import com.assessme.model.UserPasswordHistory;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: monil Created on: 2020-06-16
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserPasswordHistoryServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(UserPasswordHistoryServiceImplTest.class);


    @Mock
    private UserPasswordHistoryServiceImpl userPasswordHistoryService;

    private Optional<UserPasswordHistory> passwordHistory;

    @Test
    public void addPasswordModificationRecordTest() throws Exception {

        logger.info("Running unit test for adding the user password record");

        Long userId = 1L;
        String encryptedPassword = "$2a$10$3r12JVLTMrpCWaCEyKsvCe7Y6qBTveyqjzU.Bh/S8VtNIkYLKLl2W";
        Timestamp passwordModifiedOn = new Timestamp(Calendar.getInstance().getTime().getTime());

        UserPasswordHistory userPasswordHistory = new UserPasswordHistory(userId, encryptedPassword,
            passwordModifiedOn);
        Optional<UserPasswordHistory> optionalUserPasswordHistory = Optional
            .of(userPasswordHistory);

        Mockito.when(userPasswordHistoryService.addUserPasswordRecord(userPasswordHistory))
            .thenReturn(optionalUserPasswordHistory);

        passwordHistory = userPasswordHistoryService.addUserPasswordRecord(userPasswordHistory);

        Assert.isTrue(passwordHistory.isPresent(),
            "User password history record should not be empty");
        Assert.notNull(passwordHistory.get().getUserId(), "User id should not be null");
        Assert.notNull(passwordHistory.get().getPassword(), "user password id should not be null");
        Assertions.assertEquals(passwordHistory.get().getUserId(), userId);
        Assertions.assertEquals(passwordHistory.get().getPassword(), encryptedPassword);

    }

    @Test
    public void getUserPasswordHistory() throws Exception {

        logger.info("Running unit test for getting user's password history");

        Long userId = 1L;
        String encryptedPassword = "$2a$10$3r12JVLTMrpCWaCEyKsvCe7Y6qBTveyqjzU.Bh/S8VtNIkYLKLl2W";
        Timestamp passwordModifiedOn = new Timestamp(Calendar.getInstance().getTime().getTime());
        UserPasswordHistory userPasswordHistory = new UserPasswordHistory(userId, encryptedPassword,
            passwordModifiedOn);

        List<UserPasswordHistory> userPasswordHistoryList = new ArrayList<>();
        userPasswordHistoryList.add(userPasswordHistory);

        Mockito.when(userPasswordHistoryService.getUserPasswordHistory(userId, 1))
            .thenReturn(userPasswordHistoryList);
        Assert.notEmpty(userPasswordHistoryService.getUserPasswordHistory(userId, 1),
            "user password list is not null");

    }
}
