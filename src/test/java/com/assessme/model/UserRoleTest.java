package com.assessme.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserRoleTest {

    @Test
    public void DefaultConstructorTest() {
        UserRole userRole = new UserRole();

        Assertions.assertNull(userRole.getUserId());
        Assertions.assertNull(userRole.getRoleId());
    }

    @Test
    public void ParameterisedUserConstructorTest() {
        Long userId = 1L;
        int roleId = 1;

        UserRole userRole = new UserRole(userId, roleId);

        Assertions.assertNotNull(userRole.getUserId());
        Assertions.assertEquals(userRole.getUserId(), userId);

        Assertions.assertNotNull(userRole.getRoleId());
        Assertions.assertEquals(userRole.getRoleId(), roleId);
    }

    @Test
    public void setUserIdTest() {
        Long userId = 1L;
        UserRole userRole = new UserRole();
        Assertions.assertNull(userRole.getUserId());

        userRole.setUserId(userId);
        Assertions.assertNotNull(userRole.getUserId());
        Assertions.assertEquals(userRole.getUserId(), userId);
    }

    @Test
    public void setRoleIdTest() {
        int roleId = 1;
        UserRole userRole = new UserRole();
        Assertions.assertNull(userRole.getRoleId());

        userRole.setRoleId(roleId);
        Assertions.assertNotNull(userRole.getRoleId());
        Assertions.assertEquals(userRole.getRoleId(), roleId);
    }
}
