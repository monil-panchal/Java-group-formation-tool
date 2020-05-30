package com.assessme.service;

import com.assessme.db.dao.RoleDAOImpl;
import com.assessme.model.Role;
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
 * Created on: 2020-05-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RoleServiceTest {

    private Logger logger = LoggerFactory.getLogger(RoleServiceTest.class);

    @Mock
    private RoleDAOImpl roleDAOImpl;

    private Optional<Role> role;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void getRoleFromRoleNameTest() throws Exception {
        Mockito.when(roleDAOImpl.getRolebyName("ADMIN")).thenReturn(Optional.of(new Role(1, "ADMIN")));

        role = roleService.getRoleFromRoleName("ADMIN");
        Assert.isTrue(role.isPresent(), "Role should not be empty");
        Assertions.assertEquals(role.get().getRoleName(), "ADMIN");
        Assert.notNull(role.get().getRoleId(), "Role id should not be null");
    }
}