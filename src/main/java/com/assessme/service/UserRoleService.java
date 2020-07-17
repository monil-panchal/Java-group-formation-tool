package com.assessme.service;

import com.assessme.model.UserRole;
import java.util.Optional;

/**
 * @author: monil Created on: 2020-05-30
 */
public interface UserRoleService {

    Optional<UserRole> addUserRole(Long userId, Integer roleId) throws Exception;
}
