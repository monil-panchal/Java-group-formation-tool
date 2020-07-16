package com.assessme.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UserTokenTest {

    @Test
    public void DefaultConstructorTest() {
        UserToken userToken = new UserToken();

        Assertions.assertNull(userToken.getToken());
        Assertions.assertNull(userToken.getUserId());
    }

    @Test
    public void ParameterisedConstructorTest() {
        Long userId = 1L;
        String token = "userToken";

        UserToken userToken = new UserToken(userId, token);

        Assertions.assertNotNull(userToken.getToken());
        Assertions.assertEquals(userToken.getUserId(), userId);

        Assertions.assertNotNull(userToken.getUserId());
        Assertions.assertEquals(userToken.getToken(), token);
    }

    @Test
    public void setUserIdTest() {
        Long userId = 1L;

        UserToken userToken = new UserToken();
        Assertions.assertNull(userToken.getUserId());

        userToken.setUserId(userId);
        Assertions.assertNotNull(userToken.getUserId());
        Assertions.assertEquals(userToken.getUserId(), userId);
    }

    @Test
    public void getUserIdTest() {
        Long userId = 1L;

        UserToken userToken = new UserToken();
        Assertions.assertNull(userToken.getUserId());

        userToken.setUserId(userId);
        Assertions.assertNotNull(userToken.getUserId());
        Assertions.assertEquals(userToken.getUserId(), userId);
    }

    @Test
    public void setTokenTest() {
        String token = "userToken";

        UserToken userToken = new UserToken();
        Assertions.assertNull(userToken.getToken());

        userToken.setToken(token);
        Assertions.assertNotNull(userToken.getToken());
        Assertions.assertEquals(userToken.getToken(), token);
    }

    @Test
    public void getTokenTest() {
        String token = "userToken";

        UserToken userToken = new UserToken();
        Assertions.assertNull(userToken.getToken());

        userToken.setToken(token);
        Assertions.assertNotNull(userToken.getToken());
        Assertions.assertEquals(userToken.getToken(), token);
    }
}
