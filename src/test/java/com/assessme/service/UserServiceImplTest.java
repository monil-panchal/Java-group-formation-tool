package com.assessme.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.assessme.auth.password.restriction.PasswordChangePolicyImpl;
import com.assessme.auth.password.restriction.RegisterPasswordPolicyImpl;
import com.assessme.db.dao.RoleDAOImpl;
import com.assessme.db.dao.UserDAOImpl;
import com.assessme.db.dao.UserPasswordHistoryDAOImpl;
import com.assessme.db.dao.UserRoleDAOImpl;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.model.UserPasswordHistory;
import com.assessme.model.UserRole;
import com.assessme.model.UserRoleDTO;
import com.assessme.model.UserToken;
import com.assessme.util.AppConstant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: monil Created on: 2020-05-29
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Mock
    private UserDAOImpl userDAO;

    private Optional<User> userFromDB;

    private Optional<UserRoleDTO> userWithRoleFromDB;

    @Mock
    private UserServiceImpl userServiceMock;

    @Mock
    private UserRoleServiceImpl userRoleServiceImpl;

    @Mock
    private RoleServiceImpl roleServiceImpl;

    @Mock
    private UserTokenServiceImpl userTokenServiceImpl;

    @Mock
    private UserPasswordHistoryServiceImpl userPasswordHistoryServiceImpl;

    @Mock
    private RoleDAOImpl roleDAOImpl;

    @Mock
    private UserRoleDAOImpl userRoleDAOImpl;

    @Mock
    private UserPasswordHistoryDAOImpl userPasswordHistoryDAOImpl;

    @Mock
    private PasswordChangePolicyImpl passwordChangePolicy;

    @Mock
    private RegisterPasswordPolicyImpl registerPasswordPolicy;

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

        Mockito.when(userServiceMock.getUserFromEmail(email)).thenReturn(Optional.of(user));

        userFromDB = userServiceMock.getUserFromEmail(email);

        Assert.isTrue(userFromDB.isPresent(), "User should not be empty");
        Assert.notNull(userFromDB.get().getBannerId(), "Banner id should not be null");
        Assert.notNull(userFromDB.get().getEmail(), "email id should not be null");
        Assertions.assertEquals(userFromDB.get().getEmail(), email);
    }

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

        Mockito.when(userServiceMock.getUserWithRolesFromEmail(email))
                .thenReturn(Optional.of(user));

        userFromDB = userServiceMock.getUserWithRolesFromEmail(email);

        Assert.isTrue(userFromDB.isPresent(), "User should not be empty");
        Assert.notNull(userFromDB.get().getBannerId(), "Banner id should not be null");
        Assert.notNull(userFromDB.get().getEmail(), "email id should not be null");
        Assertions.assertEquals(userFromDB.get().getEmail(), email);
        Assert.notEmpty(userFromDB.get().getUserRoles(), "User role list should not be empty");

    }

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

        Mockito.when(userServiceMock.getUserList()).thenReturn(Optional.of(userList));
        Assert.notEmpty(userServiceMock.getUserList().get(), "user list is not null");

    }

    @Test
    public void addUserTest() throws Exception {

        logger.info("Running unit test to add new User");

        User user = new User();
        user.setBannerId("B00838558");
        user.setFirstName("Monil");
        user.setLastName("Panchal");
        user.setEmail("test@email.com");
        user.setUserId(1L);

        Optional<User> optionalUserObject = Optional.of(user);

        UserRole userRole = new UserRole(1L, 1);
        Optional<UserRole> userRoleOptional = Optional.of(userRole);

        Role role = new Role();
        role.setRoleName(AppConstant.DEFAULT_USER_ROLE_CREATE);
        role.setRoleId(2);

        Optional<Role> optionalRole = Optional.of(role);

        Mockito.when(registerPasswordPolicy.isSatisfied(user.getPassword())).thenReturn(true);

        Mockito.when(userDAO.addUser(user)).thenReturn(Optional.of(user));
        Mockito.when(roleServiceImpl.getRoleFromRoleName(AppConstant.DEFAULT_USER_ROLE_CREATE))
                .thenReturn(optionalRole);
        Mockito.when(userRoleServiceImpl.addUserRole(user.getUserId(), role.getRoleId()))
                .thenReturn(userRoleOptional);
        Mockito.when(userServiceMock.addUser(user, AppConstant.DEFAULT_USER_ROLE_CREATE))
                .thenReturn(optionalUserObject);

        userFromDB = userServiceMock.addUser(user, AppConstant.DEFAULT_USER_ROLE_CREATE);

        Assert.isTrue(userFromDB.isPresent(), "Updated User object should not be empty");
        Assert.notNull(userFromDB.get().getEmail(), "User email should not be null");

    }

    /**
     * Darshan Insert User Test
     */

    @Test
    void insertUser() throws Exception {
        User user = new User("B00123456", "Doe",
                "John", "john.doe@email.com", "password", true);
        Mockito.when(userDAO.addUser(user)).thenReturn(Optional.of(user));
        assertTrue(userDAO.addUser(user).isPresent());
        verify(userDAO, times(1)).addUser(user);
    }

    @Test
    public void updateUserRoleTest() throws Exception {

        logger.info("Running unit test for updating the user role");

        User user = new User();
        user.setBannerId("B00838558");
        user.setActive(true);
        user.setFirstName("Monil");
        user.setLastName("Panchal");
        user.setUserId(1l);
        user.setEmail("testUser@email.com");

        Optional<User> optionalUserObject = Optional.of(user);

        String newRole = "STUDENT";

        Role role = new Role();
        role.setRoleName(newRole);
        role.setRoleId(2);

        Optional<Role> optionalRoleObject = Optional.of(role);

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setEmail(user.getEmail());
        userRoleDTO.setUserRoles(Set.of("TA", "STUDENT"));

        Mockito.when(userServiceMock.getUserFromEmail(user.getEmail()))
                .thenReturn(optionalUserObject);
        Mockito.when(roleServiceImpl.getRoleFromRoleName(newRole)).thenReturn(optionalRoleObject);

        Optional<UserRole> userRoleOptional = Optional.of(new UserRole());
        Mockito.when(userRoleServiceImpl
                .addUserRole(optionalUserObject.get().getUserId(),
                        optionalRoleObject.get().getRoleId()))
                .thenReturn(userRoleOptional);

        Mockito.when(userServiceMock.updateUserRole(optionalUserObject.get(), newRole))
                .thenReturn(Optional.of(userRoleDTO));
        userWithRoleFromDB = userServiceMock.updateUserRole(optionalUserObject.get(), newRole);

        Assert.isTrue(userWithRoleFromDB.isPresent(), "Updated User object should not be empty");
        Assert.notNull(userWithRoleFromDB.get().getEmail(), "User email should not be null");
        Assert.notEmpty(userWithRoleFromDB.get().getUserRoles(),
                "User role list should not be empty");

    }

    @Test
    public void addUserTokenTest() throws Exception {

        User user = new User();
        user.setBannerId("B00838558");
        user.setFirstName("Monil");
        user.setLastName("Panchal");
        user.setEmail("test@email.com");
        user.setUserId(1L);
        Optional<User> optionalUserObject = Optional.of(user);

        UserToken userToken = new UserToken(1L, "b655675a-aa70-42da-9827-25dc70439351");
        Optional<UserToken> newUserToken = Optional.of(userToken);

        Mockito.when(userServiceMock.getUserFromEmail(user.getEmail()))
                .thenReturn(optionalUserObject);
        Mockito.when(userTokenServiceImpl.addUserToken(userToken)).thenReturn(newUserToken);

        Assert.isTrue(optionalUserObject.isPresent(), " User object should not be empty");
        Assert.isTrue(newUserToken.isPresent(), " User token object should not be empty");
        Assert.notNull(newUserToken.get().getToken(), "User token should not be null");

    }

    @Test
    public void getUserTokenTest() throws Exception {

        User user = new User();
        user.setBannerId("B00838558");
        user.setFirstName("Monil");
        user.setLastName("Panchal");
        user.setEmail("test@email.com");
        user.setUserId(1L);
        Optional<User> optionalUserObject = Optional.of(user);

        UserToken userToken = new UserToken(1L, "b655675a-aa70-42da-9827-25dc70439351");
        Optional<UserToken> newUserToken = Optional.of(userToken);

        Mockito.when(userTokenServiceImpl.getUserToken(user.getUserId())).thenReturn(newUserToken);

        Assert.isTrue(newUserToken.isPresent(), " User token object should not be empty");
        Assert.notNull(newUserToken.get().getToken(), "User token should not be null");

    }

    @Test
    public void updateUserPasswordTest() throws Exception {

        logger.info("Running unit test for updating the user password");

        User user = new User();
        user.setBannerId("B00838558");
        user.setActive(true);
        user.setFirstName("Monil");
        user.setLastName("Panchal");
        user.setUserId(1l);
        user.setPassword("PassWord");
        user.setEmail("testUser@email.com");

        Optional<User> optionalUserObject = Optional.of(user);

        passwordChangePolicy.addPasswordRestrictions(user.getUserId());
        Mockito.when(passwordChangePolicy.isSatisfied(user.getPassword())).thenReturn(true);

        Mockito.when(userServiceMock.getUserFromEmail(user.getEmail()))
                .thenReturn(optionalUserObject);
        Mockito.when(userServiceMock.updateUserPassword(user, "new password"))
                .thenReturn(optionalUserObject);

        UserPasswordHistory userPasswordHistory = new UserPasswordHistory(user.getUserId(),
                user.getPassword());
        Optional<UserPasswordHistory> optionalUserPasswordHistory = Optional
                .of(userPasswordHistory);

        Mockito.when(userPasswordHistoryServiceImpl.addUserPasswordRecord(userPasswordHistory))
                .thenReturn(optionalUserPasswordHistory);

        Assert.isTrue(optionalUserPasswordHistory.isPresent(),
                "UserPasswordHistory object should not be empty");

        Assert.isTrue(optionalUserObject.isPresent(), "Updated User object should not be empty");
        userFromDB = userServiceMock.updateUserPassword(user, "new password");

        Assert.isTrue(userFromDB.isPresent(), "Updated User object should not be empty");
        Assert.notNull(userFromDB.get().getEmail(), "User email should not be null");
        Assertions.assertEquals(userFromDB.get().getPassword(), user.getPassword());

    }

}
