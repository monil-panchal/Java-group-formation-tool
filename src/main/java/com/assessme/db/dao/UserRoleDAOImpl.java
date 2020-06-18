package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.assessme.db.CallStoredProcedure;

/**
 * @author: monil
 * Created on: 2020-05-30
 */

/**
 * The {@link UserRoleDAO} implementation class for performing CRUD operations for the user_role table of the database.
 */
@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

    private Logger logger = LoggerFactory.getLogger(UserRoleDAOImpl.class);

    private final ConnectionManager connectionManager;

    public UserRoleDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    @Override
    public Boolean addUserRole(UserRole userRole) throws Exception {

        CallStoredProcedure procedure = null;
        try {
            if (userRole == null || userRole.getRoleId() == null || userRole.getUserId() == null)
                throw new Exception("UserRole cannot be null");

            // Insert user_role record
            procedure = new CallStoredProcedure("spAddUserRole(?,?)");
            // SQL query for inserting the user_role record

            //Setting the query params

            procedure.setParameter(1, userRole.getUserId());
            procedure.setParameter(2, userRole.getRoleId());

//            // Executing the query to store the user role record
            int row = procedure.executeUpdate();

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String.format("User role record has been successfully inserted in the DB");
                logger.info(successString);

            } else {
                String failureString = String.format("Failed to insert the user role record record in the DB");
                logger.error(failureString);
                throw new Exception(failureString);
            }
            return true;

        } catch (Exception e) {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
    }
}
