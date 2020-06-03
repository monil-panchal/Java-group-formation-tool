package com.assessme.service;

import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-05-31
 */
public interface UserService extends UserDetailsService {

    Optional<List<User>> getUserList() throws Exception;
    Optional<User> getUserFromEmail(String email) throws Exception;
    Optional<UserRoleDTO> getUserWithRolesFromEmail(String email) throws Exception;
    Optional<User> addUser(User user, String userRole) throws Exception;
    Optional<UserRoleDTO> updateUserRole(User user, String userRole) throws Exception;
}
