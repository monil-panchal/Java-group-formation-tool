package com.assessme.service;

import com.assessme.db.dao.UserTokenDAOImpl;
import com.assessme.model.UserToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-06-04
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserTokenServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(UserTokenServiceImplTest.class);

    @Mock
    private UserTokenDAOImpl userTokenDAOImpl;

    @InjectMocks
    private UserTokenServiceImpl userTokenServiceImpl;

    private Optional<UserToken> userTokenFromDB;

    @Test
    public void getUserTokenTest() throws Exception {

        logger.info("Running unit test for fetching the user token");

        Long userId = 1L;
        UserToken userToken = new UserToken(userId, "b655675a-aa70-42da-9827-25dc70439351");
        Optional<UserToken> optionalUserToken = Optional.of(userToken);

        Mockito.when(userTokenDAOImpl.getUserToken(userId)).thenReturn(optionalUserToken);

        userTokenFromDB = userTokenServiceImpl.getUserToken(userId);

        Assert.isTrue(userTokenFromDB.isPresent(), "User token should not be empty");
        Assert.notNull(userTokenFromDB.get().getToken(), "Token should not be null");
        Assert.notNull(userTokenFromDB.get().getUserId(), "token user id should not be null");
        Assertions.assertEquals(userTokenFromDB.get().getUserId(), userId);
    }

    @Test
    public void addUserTokenTest() throws Exception {

        logger.info("Running unit test for adding the user token");

        Long userId = 1L;
        UserToken userToken = new UserToken(userId, "b655675a-aa70-42da-9827-25dc70439351");
        Optional<UserToken> optionalUserToken = Optional.of(userToken);

        Mockito.when(userTokenDAOImpl.addUserToken(userToken)).thenReturn(optionalUserToken);

        userTokenFromDB = userTokenServiceImpl.addUserToken(userToken);

        Assert.isTrue(userTokenFromDB.isPresent(), "User token should not be empty");
        Assert.notNull(userTokenFromDB.get().getToken(), "Token should not be null");
        Assert.notNull(userTokenFromDB.get().getUserId(), "token user id should not be null");
        Assertions.assertEquals(userTokenFromDB.get().getUserId(), userId);

    }
}
