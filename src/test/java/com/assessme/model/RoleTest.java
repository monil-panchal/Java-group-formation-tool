package com.assessme.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: hardik Created on: 2020-06-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SuppressWarnings("deprecation")
public class RoleTest {

    private final Logger logger = LoggerFactory.getLogger(RoleTest.class);

    @Test
    public void ConstructorTests() {
        logger.info("Running unit test for RoleTest constructor");

        Integer roleId = 1;
        String roleName = "ADMIN";

        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(roleName);

        Assert.isTrue(role.getRoleId().equals(roleId));
        Assert.isTrue(role.getRoleName().equals(roleName));

        Role role1 = new Role(roleId, roleName);

        Assert.isTrue(role1.getRoleId().equals(roleId));
        Assert.isTrue(role1.getRoleName().equals(roleName));
    }

    @Test
    public void getRoleIdTest() {
        logger.info("Running unit test for getting getRoleId from RoleTest");

        Integer roleId = 1;

        Role role = new Role();
        role.setRoleId(roleId);

        Assert.isTrue(role.getRoleId().equals(roleId));
    }

    @Test
    public void setRoleIdTest() {
        logger.info("Running unit test for setting getRoleId from RoleTest");

        Integer roleId = 1;

        Role role = new Role();
        role.setRoleId(roleId);

        Assert.isTrue(role.getRoleId().equals(roleId));
    }

    @Test
    public void getRoleNameTest() {
        logger.info("Running unit test for getting setRoleName from RoleTest");

        String roleName = "ADMIN";

        Role role = new Role();
        role.setRoleName(roleName);

        Assert.isTrue(role.getRoleName().equals(roleName));
    }

    @Test
    public void setRoleNameTest() {
        logger.info("Running unit test for setting setRoleName from RoleTest");

        String roleName = "ADMIN";

        Role role = new Role();
        role.setRoleName(roleName);

        Assert.isTrue(role.getRoleName().equals(roleName));
    }

}
