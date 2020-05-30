package com.assessme.service;

import com.assessme.db.dao.RoleDAOImpl;
import com.assessme.db.dao.UserDAOImpl;
import com.assessme.db.dao.UserRoleDAOImpl;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import com.assessme.util.AppConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import java.util.Set;

/**
 * @author: monil
 * Created on: 2020-05-29
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    private Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Mock
    private UserDAOImpl userDAO;

    private Optional<User> userFromDB;

    @InjectMocks
    private UserService userServiceMock;

    @Mock
    private UserService userServiceMock2;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Mock
    private RoleDAOImpl roleDAOImpl;

    @Mock
    private UserRoleDAOImpl userRoleDAOImpl;

    // Unit test
    @Test
    public void getUserFromEmailTest() throws Exception {

        logger.info("Running unit test for fetching user with emailId");

        String email = "testUser@email.com";

        User user = new User();
        user.setBannerId("B00838558");
        user.setActive(true);
        user.setFirstName("John");
        user.setLastName("Abraham");
        user.setUserId(1l);
        user.setEmail(email);

        Mockito.when(userDAO.getUserByEmail(email)).thenReturn(Optional.of(user));

        userFromDB = userServiceMock.getUserFromEmail(email);

        Assert.isTrue(userFromDB.isPresent(), "User should not be empty");
        Assert.notNull(userFromDB.get().getBannerId(), "Banner id should not be null");
        Assert.notNull(userFromDB.get().getEmail(), "email id should not be null");
        Assertions.assertEquals(userFromDB.get().getEmail(), email);
    }

    // Unit test
    @Test
    public void getUserWithRolesFromEmail() throws Exception {

        logger.info("Running unit test for fetching user with roles with emailId");

        Optional<UserRoleDTO> userFromDB = Optional.empty();
        String email = "testUser@email.com";
        Set<String> userRoleSet = Set.of("ADMIN");

        UserRoleDTO user = new UserRoleDTO();
        user.setBannerId("B00838558");
        user.setActive(true);
        user.setFirstName("John");
        user.setLastName("Abraham");
        user.setUserId(1l);
        user.setEmail(email);
        user.setUserRoles(userRoleSet);

        Mockito.when(userDAO.getUserWithRolesFromEmail(email)).thenReturn(Optional.of(user));

        userFromDB = userServiceMock.getUserWithRolesFromEmail(email);

        Assert.isTrue(userFromDB.isPresent(), "User should not be empty");
        Assert.notNull(userFromDB.get().getBannerId(), "Banner id should not be null");
        Assert.notNull(userFromDB.get().getEmail(), "email id should not be null");
        Assertions.assertEquals(userFromDB.get().getEmail(), email);
        Assert.notEmpty(userFromDB.get().getUserRoles(), "User role list should not be empty");

    }

    //Unit test
    @Test
    public void getUserListTest() throws Exception {

        logger.info("Running unit test for fetching all users");

        User user = new User();
        user.setBannerId("B00838558");
        user.setActive(true);
        user.setFirstName("John");
        user.setLastName("Abraham");
        user.setUserId(1l);
        user.setEmail("testUser@email.com");

        List<User> userList = List.of(user);

        Mockito.when(userDAO.getAllUser()).thenReturn(userList);
        Assert.notEmpty(userServiceMock.getUserList().get(), "user list is not null");

    }


    //Unit test
    @Test
    public void addUserTest() throws Exception {

        logger.info("Running unit test to add new User");

        User user = new User();

        user.setBannerId("B0011111");
        user.setFirstName("John");
        user.setLastName("Abraham");
        user.setEmail("test@email.com");

        Mockito.when(userDAO.addUser(user)).thenReturn(Optional.of(user));

        userFromDB = userServiceMock2.addUser(user, AppConstant.DEFAULT_USER_ROLE_CREATE);

//        Assert.isTrue(userFromDB.isPresent(), "User should not be empty");
//        Assert.notNull(userFromDB.get().getBannerId(), "Banner id should not be null");
//        Assert.notNull(userFromDB.get().getEmail(), "email id should not be null");
//        Assertions.assertEquals(userFromDB.get().getActive(), true);
//        Assertions.assertEquals(userFromDB.get().getPassword(), "B00838558" + "_" + "Abraham");

    }

    //Integration test
    @Test
    public void getUserFromEmailIntegrationTests() throws Exception {

        String email = "monil.panchal@dal.ca";
        logger.info(String.format("Running Integration test for fetching user with emailId: " + email));

        // Calling the actual service
        Optional<User> dbUser = userService.getUserFromEmail(email);

        //verification
        Assert.notNull(dbUser, String.format("User with email id: %s should not be null", email));
        Assertions.assertEquals(dbUser.get().getEmail(), email);

    }
}
