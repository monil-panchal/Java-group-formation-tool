package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.UserRole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author: monil Created on: 2020-05-30
 */

/**
 * The {@link UserRoleDAO} implementation class for performing CRUD operations for the user_role
 * table of the database.
 */
@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

  private final Logger logger = LoggerFactory.getLogger(UserRoleDAOImpl.class);

  private final ConnectionManager connectionManager;

  public UserRoleDAOImpl() {
    connectionManager = new ConnectionManager();
  }

  @Override
  public Boolean addUserRole(UserRole userRole) throws Exception {
    String insertUserSQLQuery = "INSERT INTO user_role values (?,?) ON DUPLICATE KEY UPDATE user_id=LAST_INSERT_ID(user_id)";

    try (
        Connection connection = connectionManager.getDBConnection().get();
        PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQLQuery,
            Statement.RETURN_GENERATED_KEYS)

    ) {
      if (userRole == null || userRole.getRoleId() == null || userRole.getUserId() == null) {
        throw new Exception("UserRole cannot be null");
      }

      //Setting the query params
      preparedStatement.setLong(1, userRole.getUserId());
      preparedStatement.setInt(2, userRole.getRoleId());

      // Executing the query to store the user role record
      int row = preparedStatement.executeUpdate();

      // check if the record was inserted successfully
      if (row > 0) {
        String successString = "User role record has been successfully inserted in the DB";
        logger.info(successString);

      } else {
        String failureString = "Failed to insert the user role record record in the DB";
        logger.error(failureString);
        throw new Exception(failureString);
      }

      return true;

    } catch (Exception e) {
      //Closing the connection
      logger.error(e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }
}
