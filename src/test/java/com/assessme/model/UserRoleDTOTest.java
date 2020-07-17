package com.assessme.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
