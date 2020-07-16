package com.assessme.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRoleDTOTest {

    @Test
    public void setUserRolesTest() {
        Set<String> roles = new HashSet<>(
                List.of("ADMIN",
                        "INSTRUCTOR",
                        "STUDENT",
                        "TA",
                        "GUEST"));
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        Assertions.assertNull(userRoleDTO.getUserRoles());

        userRoleDTO.setUserRoles(roles);
        Assertions.assertNotNull(userRoleDTO.getUserRoles());
        Assertions.assertEquals(userRoleDTO.getUserRoles(), roles);
    }

    @Test
    public void getUserRolesTest() {
        Set<String> roles = new HashSet<>(
                List.of("ADMIN",
                        "INSTRUCTOR",
                        "STUDENT",
                        "TA",
                        "GUEST"));
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        Assertions.assertNull(userRoleDTO.getUserRoles());

        userRoleDTO.setUserRoles(roles);
        Assertions.assertNotNull(userRoleDTO.getUserRoles());
        Assertions.assertEquals(userRoleDTO.getUserRoles(), roles);
    }
}
