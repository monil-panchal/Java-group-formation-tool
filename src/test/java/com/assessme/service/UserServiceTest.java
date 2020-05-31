package com.assessme.service;

import com.assessme.db.dao.UserDAOImpl;
import com.assessme.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-05-29
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    private Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Mock
    private UserDAOImpl userDAO;

    @MockBean
    private User user;

    @Autowired
    private UserService userService;


    // Unit test
    @Test
    public void getUserWithEmailTest() throws Exception {

        logger.info("Running unit test for fetching user with emailId");

        String email = "testUser@email.com";

        user.setBannerId("B00838558");
        user.setActive(true);
        user.setFirstName("John");
        user.setLastName("Abraham");
        user.setUserId(1);

        Mockito.when(userDAO.getUserByEmail(email)).thenReturn(Optional.ofNullable(user));

    }

    //Unit test
    @Test
    public void getUserListTest() throws Exception {

        logger.info("Running unit test for fetching all users");

        List<User> userList = List.of(user);
        Mockito.when(userDAO.getAllUser()).thenReturn(userList);
        Assert.notEmpty(userDAO.getAllUser(), "user list is not null");

    }

    /*//Integration test
    @Test
    public void getUserWithEmailIntegrationTests() throws Exception {

        String email = '\''+ "monil.panchal@dal.ca" + '\'';
        logger.info(String.format("Running Integration test for fetching user with emailId: " + email));

        // Calling the actual service
        Optional<User> dbUser = userService.getUserWithEmail(email);

        //verification
        Assert.notNull(dbUser, String.format("User with email id: %s should not be null", email));

    }*/
}
