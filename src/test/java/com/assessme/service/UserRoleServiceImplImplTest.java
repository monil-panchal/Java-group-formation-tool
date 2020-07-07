package com.assessme.service;

import com.assessme.db.dao.UserRoleDAOImpl;
import com.assessme.model.UserRole;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: monil Created on: 2020-05-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserRoleServiceImplImplTest {

  private final Logger logger = LoggerFactory.getLogger(UserRoleServiceImplImplTest.class);

  @Mock
  private UserRoleDAOImpl userRoleDAOImpl;

  private Optional<UserRole> userRole;

  @InjectMocks
  private UserRoleServiceImpl userRoleServiceImpl;

  @Test
  public void addUserRoleTest() throws Exception {

    UserRole newUserRole = new UserRole(1L, 1);

    Mockito.when(userRoleDAOImpl.addUserRole(newUserRole)).thenReturn(true);

    userRole = userRoleServiceImpl.addUserRole(1L, 1);

    Assert.isTrue(userRole.isPresent(), "User role should not be empty");
    Assert.notNull(userRole.get().getRoleId(), "Role id should not be null");
    Assert.notNull(userRole.get().getUserId(), "user id should not be null");
    Assertions.assertEquals(userRole.get().getUserId(), 1L);
    Assertions.assertEquals(userRole.get().getRoleId(), 1);

  }
}
