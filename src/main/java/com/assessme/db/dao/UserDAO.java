package com.assessme.db.dao;

/**
 * @author: monil Created on: 2020-05-29
 */

import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import java.util.List;
import java.util.Optional;

/**
 * The implementation of this class provides CRUD operations for the user table of the database.
 */

public interface UserDAO {

    Optional<User> getUserById(long id) throws Exception;

    List<User> getAllUser() throws Exception;

    Optional<User> getUserByEmail(String email) throws Exception;

    Optional<UserRoleDTO> getUserWithRolesFromEmail(String email) throws Exception;

    Optional<User> addUser(User user) throws Exception;

    Optional<User> updateUserPassword(User user) throws Exception;

    List<User> getUserNotAssignedForCourse(long courseId) throws Exception;
}