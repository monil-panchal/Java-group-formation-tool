package com.assessme.service;

/**
 * @author: monil Created on: 2020-05-30
 */

import com.assessme.db.dao.RoleDAOImpl;
import com.assessme.model.Role;
import com.assessme.util.AppConstant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    private static RoleServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleDAOImpl roleDAOImpl;

    public RoleServiceImpl() {
        this.roleDAOImpl = RoleDAOImpl.getInstance();
    }

    public static RoleServiceImpl getInstance() {
        if (instance == null) {
            instance = new RoleServiceImpl();
        }
        return instance;
    }

    public Optional<Role> getRoleFromRoleName(String roleName) throws Exception {

        Optional<Role> role;
        try {

            if (roleName == null || roleName.isEmpty() || roleName.isBlank()) {
                roleName = AppConstant.DEFAULT_USER_ROLE_CREATE;
            }
            role = roleDAOImpl.getRolebyName(roleName);
            String resMessage = String
                .format("Role with role name: %s has been retrieved from the database", roleName);
            logger.info(resMessage);


        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the role from the database.");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return role;
    }
}