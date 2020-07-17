package com.assessme.service;

import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import com.assessme.model.UserToken;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author: monil Created on: 2020-05-31
 */
public interface UserService extends UserDetailsService {

    Optional<List<User>> getUserList() throws Exception;

    Optional<User> getUserFromEmail(String email) throws Exception;

    Optional<User> getUserById(long id) throws Exception;

    Optional<UserRoleDTO> getUserWithRolesFromEmail(String email) throws Exception;

    Optional<User> addUser(User user, String userRole) throws Exception;

    Optional<UserRoleDTO> updateUserRole(User user, String userRole) throws Exception;

    Optional<User> updateUserPassword(User user, String newPassword) throws Exception;

    Optional<UserToken> addUserToken(String email) throws Exception;

    Optional<UserToken> getUserToken(String email) throws Exception;

    Optional<List<User>> getUsersNotAssignedForCourse(String courseCode) throws Exception;
}
