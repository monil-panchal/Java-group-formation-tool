package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

/**
 * @author: monil Created on: 2020-05-30
 */

/**
 * The {@link RoleDAO} implementation class for performing CRUD operations for the role table of the database.
 */
@Repository
public class RoleDAOImpl implements RoleDAO {

  private Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);

  private ConnectionManager connectionManager;

  public RoleDAOImpl() {
    connectionManager = new ConnectionManager();

  }

  // RoleDAO method for retrieving role using role_name
  @Override
  public Optional<Role> getRolebyName(String roleName) throws Exception {

    Optional<Role> role = Optional.empty();

    // SQL query for fetching the role record based on the role_name
    roleName = '\'' + roleName + '\'';
    String selectQuery = "SELECT * FROM role WHERE role_name=" + roleName;

    logger.info(String.format("selectQuery: %s", selectQuery));

    try (
        Connection connection = connectionManager.getDBConnection().get();
        Statement statement = connection.createStatement();
    ) {
      if ((roleName != null && !roleName.isEmpty())) {
        ResultSet resultSet = statement.executeQuery(selectQuery);

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