package com.assessme.service;

/**
 * @author: monil Created on: 2020-05-30
 */

import com.assessme.db.dao.UserRoleDAOImpl;
import com.assessme.model.UserRole;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * User role service layer class for this application
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static UserRoleServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
    private final UserRoleDAOImpl userRoleDAOImpl;

    public UserRoleServiceImpl() {
        this.userRoleDAOImpl = UserRoleDAOImpl.getInstance();
    }

    public static UserRoleServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserRoleServiceImpl();
        }
        return instance;
    }

    public Optional<UserRole> addUserRole(Long userId, Integer roleId) throws Exception {

        Optional<UserRole> role;
        try {

            if (userId == null || userId == null) {
                throw new Exception("UserId or RoleId cannot be null");
            }
            role = Optional.of(new UserRole(userId, roleId));
            userRoleDAOImpl.addUserRole(role.get());

            String resMessage = String
                .format("Role: %s has been assigned to the user: %s in the database", roleId,
                    userId);
            logger.info(resMessage);

        } catch (Exception e) {
            String errMessage = String
                .format("Error in assigning the role to the user in the database.");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return role;
    }
}