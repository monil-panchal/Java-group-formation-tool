package com.assessme.service;

import com.assessme.model.Role;
import java.util.Optional;

/**
 * @author: monil Created on: 2020-05-30
 */

public interface RoleService {

    Optional<Role> getRoleFromRoleName(String roleName) throws Exception;
}
