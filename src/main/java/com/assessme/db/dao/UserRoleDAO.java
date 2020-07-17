package com.assessme.db.dao;

/**
 * @author: monil Created on: 2020-05-30
 */

import com.assessme.model.UserRole;

/**
 * The implementation of this class provides CRUD operations for the user_role table of the
 * database.
 */

public interface UserRoleDAO {

    Boolean addUserRole(UserRole userRole) throws Exception;
}
