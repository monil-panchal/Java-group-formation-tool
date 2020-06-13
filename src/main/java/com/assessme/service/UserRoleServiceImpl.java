package com.assessme.service;

/**
 * @author: monil
 * Created on: 2020-05-30
 */

import com.assessme.db.dao.UserRoleDAOImpl;
import com.assessme.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User role service layer class for this application
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    private UserRoleDAOImpl userRoleDAOImpl;

    public UserRoleServiceImpl(UserRoleDAOImpl userRoleDAOImpl) {
        this.userRoleDAOImpl = userRoleDAOImpl;
    }

    /**
     * Service method for inserting user role
     */
    public Optional<UserRole> addUserRole(Long userId, Integer roleId) throws Exception {

        Optional<UserRole> role;
        try {

            if (userId == null || userId == null) {
                throw new Exception("UserId or RoleId cannot be null");
            }
            role = Optional.of(new UserRole(userId, roleId));
            userRoleDAOImpl.addUserRole(role.get());

            String resMessage = String.format("Role: %s has been assigned to the user: %s in the database", roleId, userId);
            logger.info(resMessage);

        } catch (Exception e) {
            String errMessage = String.format("Error in assigning the role to the user in the database.");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return role;
    }
}