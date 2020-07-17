package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.User;
import com.assessme.model.UserRoleDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-05-29
 */

public class UserDAOImpl implements UserDAO {

    private static UserDAOImpl instance;
    final String selectQuery =
            "SELECT u.user_id, u.banner_id, u.first_name, u.last_name, u.email, u.isActive " +
                    "FROM user AS u WHERE email = ?";
    final String selectUserByIdQuery =
            "SELECT u.user_id, u.banner_id, u.first_name, u.last_name, u.email, u.isActive " +
                    "FROM user AS u WHERE user_id = ?";
    final String selectUserQuery = "SELECT u.banner_id, u.first_name, u.last_name, u.email,"
            + " u.isActive FROM user AS u";
    final String selectQueryFromEmail =
            "SELECT u.banner_id, u.first_name, u.last_name, u.email, u.isActive, u.password, " +
                    "r.role_name FROM user AS u" +
                    " INNER JOIN user_role AS ur " +
                    " ON u.user_id = ur.user_id " +
                    " INNER JOIN role AS r " +
                    " ON ur.role_id = r.role_id " +
                    " WHERE u.email = ?";
    final String insertUserSQLQuery =
            "INSERT INTO user(banner_id, first_name, last_name, email, password, isActive)" +
                    "  values (?,?,?,?,?,?)";
    final String updateUserSQLQuery = "UPDATE user set password = ? where email = ?";
    final String getUserNotAssignedForCourse =
            "SELECT * FROM (SELECT u.*, ifnull(e.course_id,-1) AS course_id "
                    + "FROM user u LEFT JOIN user_course_role e ON u.user_id=e.user_id GROUP BY user_id)"
                    + " as user WHERE  course_id!=?";
    private final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    private final ConnectionManager connectionManager;

    public UserDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws Exception {

        Optional<User> user = Optional.empty();

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement statement = connection.prepareStatement(selectQuery)
        ) {
            if ((!email.isEmpty() && email != null)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("No user found in the database"));
                    throw new Exception(String.format("No user found in the database"));
                }

                logger.info(String.format("User data retrieved successfully"));

                while (resultSet.next()) {
                    user = Optional.of(new User());
                    user.get().setUserId(resultSet.getLong("user_id"));
                    user.get().setBannerId(resultSet.getString("banner_id"));
                    user.get().setFirstName(resultSet.getString("first_name"));
                    user.get().setLastName(resultSet.getString("last_name"));
                    user.get().setEmail(resultSet.getString("email"));
                    user.get().setActive(resultSet.getBoolean("isActive"));
                }
                String successString = String.format("User list retrieved successfully.");
                logger.info(successString);
            } else {
                throw new Exception(String.format("User email id cannot be null"));
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    @Override
    public Optional<User> getUserById(long id) throws Exception {
        Optional<User> user = Optional.empty();
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement statement = connection.prepareStatement(selectUserByIdQuery)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                logger.error(String.format("No user found in the database"));
                throw new Exception(String.format("No user found in the database"));
            }
            logger.info(String.format("User data retrieved successfully"));
            while (resultSet.next()) {
                user = Optional.of(new User());
                user.get().setUserId(resultSet.getLong("user_id"));
                user.get().setBannerId(resultSet.getString("banner_id"));
                user.get().setFirstName(resultSet.getString("first_name"));
                user.get().setLastName(resultSet.getString("last_name"));
                user.get().setEmail(resultSet.getString("email"));
                user.get().setActive(resultSet.getBoolean("isActive"));
            }
            String successString = String.format("User list retrieved successfully.");
            logger.info(successString);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return user;
    }


    @Override
    public List<User> getAllUser() throws Exception {

        List<User> userList = new ArrayList<>();

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection.prepareStatement(selectUserQuery)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setBannerId(resultSet.getString("banner_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setActive(resultSet.getBoolean("isActive"));
                userList.add(user);
            }
            logger.info(String.format("User list retrieved from the database: %s", userList));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return userList;
    }

    @Override
    public Optional<UserRoleDTO> getUserWithRolesFromEmail(String email) throws Exception {
        Optional<UserRoleDTO> user = Optional.empty();

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement statement = connection.prepareStatement(selectQueryFromEmail)
        ) {
            if ((!email.isEmpty() && email != null)) {
                statement.setString(1, email);
                logger.info(statement.toString());
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("User: %s is not found in the database", email));
                    throw new Exception(
                            String.format("User: %s is not found in the database", email));
                }
                logger.info(String.format("User data retrieved successfully"));
                user = Optional.of(new UserRoleDTO());
                Set<String> userRoles = new HashSet();

                resultSet.next();
                user.get().setBannerId(resultSet.getString("banner_id"));
                user.get().setFirstName(resultSet.getString("first_name"));
                user.get().setLastName(resultSet.getString("last_name"));
                user.get().setEmail(resultSet.getString("email"));
                user.get().setActive(resultSet.getBoolean("isActive"));
                user.get().setPassword(resultSet.getString("password"));
                userRoles.add(resultSet.getString("role_name"));

                while (resultSet.next()) {
                    userRoles.add(resultSet.getString("role_name"));
                }
                user.get().setUserRoles(userRoles);
                String successString = String
                        .format("User with email: %s retrieved successfully.", email);
                logger.info(successString);
            } else {
                throw new Exception(String.format("User email id cannot be null"));
            }
        } catch (Exception e) {

            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    @Override
    public Optional<User> addUser(User user) throws Exception {

        Optional<User> newUser = Optional.empty();

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(insertUserSQLQuery, Statement.RETURN_GENERATED_KEYS)
        ) {

            preparedStatement.setString(1, user.getBannerId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setBoolean(6, user.getActive());

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                String successString = String
                        .format("User record with email: %s has been successfully inserted in the DB",
                                user.getEmail());
                logger.info(successString);

            } else {
                String failureString = String
                        .format("Failed to insert the user with email: %s record in the DB",
                                user.getEmail());
                logger.error(failureString);
                throw new Exception(failureString);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getLong(1));
                    newUser = Optional.of(user);
                } else {
                    throw new SQLException("Creation of user failed. Cannot obtain user_id.");
                }
            }
            return newUser;

        } catch (Exception e) {

            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Optional<User> updateUserPassword(User user) throws Exception {
        Optional<User> updatedUserObj = Optional.empty();
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(updateUserSQLQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                String successString = String
                        .format("User record with email: %s has been successfully updated in the DB",
                                user.getEmail());
                logger.info(successString);

            } else {
                String failureString = String
                        .format("Failed to update the user with email: %s record in the DB",
                                user.getEmail());
                logger.error(failureString);
                throw new Exception(failureString);
            }
            return updatedUserObj;
        } catch (Exception e) {

            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> getUserNotAssignedForCourse(long courseId) throws Exception {
        List<User> userList = new ArrayList<>();
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(getUserNotAssignedForCourse)
        ) {
            preparedStatement.setLong(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setBannerId(resultSet.getString("banner_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setActive(resultSet.getBoolean("isActive"));
                userList.add(user);
            }
            logger.info(String.format("User list retrieved from the database: %s", userList));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return userList;
    }

}
