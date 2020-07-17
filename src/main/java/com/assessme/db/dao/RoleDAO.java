package com.assessme.db.dao;

/**
 * @author: monil Created on: 2020-05-29
 */

import com.assessme.model.Role;
import java.util.Optional;

/**
 * The implementation of this class provides CRUD operations for the role table of the database.
 */

public interface RoleDAO {

    Optional<Role> getRolebyName(String roleName) throws Exception;
}