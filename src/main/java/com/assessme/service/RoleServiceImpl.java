package com.assessme.service;

/**
 * @author: monil
 * Created on: 2020-05-30
 */

import com.assessme.db.dao.RoleDAOImpl;
import com.assessme.model.Role;
import com.assessme.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Role service layer class for this application
 */
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    private RoleDAOImpl roleDAOImpl;

    public RoleServiceImpl(RoleDAOImpl roleDAOImpl) {
        this.roleDAOImpl = roleDAOImpl;
    }

    /**
     * Service method for retrieving role based on role_name
     */
    public Optional<Role> getRoleFromRoleName(String roleName) throws Exception {

        Optional<Role> role;
        try {

            if (roleName == null || roleName.isEmpty() || roleName.isBlank()) {
                roleName = AppConstant.DEFAULT_USER_ROLE_CREATE;
            }
            role = roleDAOImpl.getRolebyName(roleName);
            String resMessage = String.format("Role with role name: %s has been retrieved from the database", roleName);
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