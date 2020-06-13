package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

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

    private DBConnectionBuilder dbConnectionBuilder;
    private Optional<Connection> connection;

    public UserRoleDAOImpl(DBConnectionBuilder dbConnectionBuilder) {
        this.dbConnectionBuilder = dbConnectionBuilder;
    }

    @Override
    public Boolean addUserRole(UserRole userRole) throws Exception {

        // Getting the DB connection
        connection = dbConnectionBuilder.createDBConnection();

        try {
            if (userRole == null || userRole.getRoleId() == null || userRole.getUserId() == null)
                throw new Exception("UserRole cannot be null");

            // Insert user_role record

            // SQL query for inserting the user_role record
            String insertUserSQLQuery = "INSERT INTO user_role values (?,?) ON DUPLICATE KEY UPDATE user_id=LAST_INSERT_ID(user_id)";

            PreparedStatement preparedStatement = connection.get().prepareStatement(insertUserSQLQuery,
                    Statement.RETURN_GENERATED_KEYS);

            //Setting the query params
            preparedStatement.setLong(1, userRole.getUserId());
            preparedStatement.setInt(2, userRole.getRoleId());

            // Executing the query to store the user role record
            int row = preparedStatement.executeUpdate();

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String.format("User role record has been successfully inserted in the DB");
                logger.info(successString);

            } else {
                String failureString = String.format("Failed to insert the user role record record in the DB");
                logger.error(failureString);
                throw new Exception(failureString);
            }

            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());
            return true;

        } catch (Exception e) {
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
