package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import com.assessme.db.CallStoredProcedure;

/**
 * @author: monil
 * Created on: 2020-05-30
 */

/**
 * The {@link RoleDAO} implementation class for performing CRUD operations for the role table of the database.
 */
@Repository
public class RoleDAOImpl implements RoleDAO {

    private Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);

    private DBConnectionBuilder dbConnectionBuilder;

    public RoleDAOImpl(DBConnectionBuilder dbConnectionBuilder) {
        this.dbConnectionBuilder = dbConnectionBuilder;
    }

    // RoleDAO method for retrieving role using role_name
    @Override
    public Optional<Role> getRolebyName(String roleName) throws Exception {

        Optional<Role> role = Optional.empty();

//        roleName = '\'' + roleName + '\'';
        CallStoredProcedure procedure = null;

        try {
            if ((roleName != null && !roleName.isEmpty())) {
                procedure = new CallStoredProcedure(dbConnectionBuilder, "spFindRoleByRoleName(?)");
                procedure.setParameter(1, roleName);
                ResultSet resultSet = procedure.getResultSet();

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("No role found in the database"));
                    throw new Exception(String.format("No role found in the database"));
                }

                logger.info(String.format("Role data retrieved successfully"));

                // Iterating through the rows and constructing user object
                while (resultSet.next()) {

                    role = Optional.of(new Role());
                    role.get().setRoleId(resultSet.getInt("role_id"));
                    role.get().setRoleName(resultSet.getString("role_name"));

                }
                String successString = String.format("Role: %s retrieved successfully.", roleName);
                logger.info(successString);

            } else
                throw new Exception(String.format("roleName is null or empty"));

        } catch (Exception e) {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }

        return role;
    }
}