package com.assessme.model;

import java.sql.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserPasswordHistoryTest {

    private final Logger logger = LoggerFactory.getLogger(UserPasswordHistoryTest.class);

    @Test
    public void DefaultConstructorTest() {
        UserPasswordHistory userPasswordHistory = new UserPasswordHistory();

        Assertions.assertNull(userPasswordHistory.getUserId());
        Assertions.assertNull(userPasswordHistory.getPassword());
        Assertions.assertNull(userPasswordHistory.getModifiedOn());
    }

    @Test
    public void ParameterisedConstructorTest() {
        Long userId = 1L;
        String password = "password";
        Timestamp modifiedOn = new Timestamp(System.currentTimeMillis());

        UserPasswordHistory userPasswordHistory = new UserPasswordHistory(userId, password,
            modifiedOn);

        Assertions.assertNotNull(userPasswordHistory.getUserId());
        Assertions.assertEquals(userPasswordHistory.getUserId(), userId);

        Assertions.assertNotNull(userPasswordHistory.getPassword());
        Assertions.assertEquals(userPasswordHistory.getPassword(), password);

        Assertions.assertNotNull(userPasswordHistory.getModifiedOn());
        Assertions.assertEquals(userPasswordHistory.getModifiedOn(), modifiedOn);
    }

    @Test
    public void setUserIdTest() {
        Long userId = 1L;
        UserPasswordHistory userPasswordHistory = new UserPasswordHistory();
        Assertions.assertNull(userPasswordHistory.getUserId());

        userPasswordHistory.setUserId(userId);
        Assertions.assertNotNull(userPasswordHistory.getUserId());
        Assertions.assertEquals(userPasswordHistory.getUserId(), userId);
    }

    @Test
    public void getUserIdTest() {
        Long userId = 1L;
        UserPasswordHistory userPasswordHistory = new UserPasswordHistory();
        Assertions.assertNull(userPasswordHistory.getUserId());

        userPasswordHistory.setUserId(userId);
        Assertions.assertNotNull(userPasswordHistory.getUserId());
        Assertions.assertEquals(userPasswordHistory.getUserId(), userId);
    }

    @Test
    public void setPasswordTest() {
        String password = "password";
        UserPasswordHistory userPasswordHistory = new UserPasswordHistory();
        Assertions.assertNull(userPasswordHistory.getPassword());

        userPasswordHistory.setPassword(password);
        Assertions.assertNotNull(userPasswordHistory.getPassword());
        Assertions.assertEquals(userPasswordHistory.getPassword(), password);
    }

    @Test
    public void getPasswordTest() {
        String password = "password";
        UserPasswordHistory userPasswordHistory = new UserPasswordHistory();
        Assertions.assertNull(userPasswordHistory.getPassword());

        userPasswordHistory.setPassword(password);
        Assertions.assertNotNull(userPasswordHistory.getPassword());
        Assertions.assertEquals(userPasswordHistory.getPassword(), password);
    }

    @Test
    public void setTimestampTest() {
        Timestamp modifiedOn = new Timestamp(System.currentTimeMillis());
        UserPasswordHistory userPasswordHistory = new UserPasswordHistory();
        Assertions.assertNull(userPasswordHistory.getModifiedOn());

        userPasswordHistory.setModifiedOn(modifiedOn);
        Assertions.assertNotNull(userPasswordHistory.getModifiedOn());
        Assertions.assertEquals(userPasswordHistory.getModifiedOn(), modifiedOn);
    }

    @Test
    public void getTimestampTest() {
        Timestamp modifiedOn = new Timestamp(System.currentTimeMillis());
        UserPasswordHistory userPasswordHistory = new UserPasswordHistory();
        Assertions.assertNull(userPasswordHistory.getModifiedOn());

        userPasswordHistory.setModifiedOn(modifiedOn);
        Assertions.assertNotNull(userPasswordHistory.getModifiedOn());
        Assertions.assertEquals(userPasswordHistory.getModifiedOn(), modifiedOn);
    }
}
