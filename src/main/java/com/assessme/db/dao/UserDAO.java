package com.assessme.db.dao;

/**
 * @author: monil
 * Created on: 2020-05-29
 */

import com.assessme.model.User;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of this class provides CRUD operations for the user table of the database.
 */

public interface UserDAO {

    Optional<User> getUserByEmail(String email) throws Exception;

    List<User> getAllUser() throws Exception;

    Boolean addUser(User user) throws Exception;

    Optional<User> updateUser(User user) throws Exception;

}