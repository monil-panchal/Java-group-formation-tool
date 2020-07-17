package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-05-30
 */

public class RoleDAOImpl implements RoleDAO {

    private static RoleDAOImpl instance;
    final String selectQuery = "SELECT * FROM role WHERE role_name=?";
    private final Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);
    private final ConnectionManager connectionManager;

    public RoleDAOImpl() {
        connectionManager = new ConnectionManager();

    }

    public static RoleDAOImpl getInstance() {
        if (instance == null) {
            instance = new RoleDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<Role> getRolebyName(String roleName) throws Exception {
        Optional<Role> role = Optional.empty();
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement statement = connection.prepareStatement(selectQuery)
        ) {
            if ((roleName != null && !roleName.isEmpty())) {
                statement.setString(1, roleName);
                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("No role found in the database"));
                    throw new Exception(String.format("No role found in the database"));
                }
                logger.info(String.format("Role data retrieved successfully"));
                while (resultSet.next()) {
                    role = Optional.of(new Role());
                    role.get().setRoleId(resultSet.getInt("role_id"));
                    role.get().setRoleName(resultSet.getString("role_name"));
                }
                String successString = String.format("Role: %s retrieved successfully.", roleName);
                logger.info(successString);
            } else {
                throw new Exception(String.format("roleName is null or empty"));
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return role;
    }
}
