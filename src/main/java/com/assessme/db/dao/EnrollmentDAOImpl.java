package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.Enrollment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:42 PM
 */
public class EnrollmentDAOImpl implements EnrollmentDAO {

    private static EnrollmentDAOImpl instance;
    private final Logger logger = LoggerFactory.getLogger(EnrollmentDAOImpl.class);
    private final ConnectionManager connectionManager;

    public EnrollmentDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    public static EnrollmentDAOImpl getInstance() {
        if (instance == null) {
            instance = new EnrollmentDAOImpl();
        }
        return instance;
    }

    @Override
    public Enrollment getEnrollment(long userId, int roleId, long courseId) {
        return null;
    }

    @Override
    public boolean insertEnrollment(Enrollment enrollment) throws Exception {
        String insertUserSQLQuery = "INSERT INTO user_course_role values (?,?,?)";
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(insertUserSQLQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            if (enrollment.getUserId() != null && enrollment.getCourseId() != null
                    && enrollment.getRoleId() != null) {

                preparedStatement.setLong(1, enrollment.getUserId());
                preparedStatement.setLong(2, enrollment.getCourseId());
                preparedStatement.setInt(3, enrollment.getRoleId());

                int row = preparedStatement.executeUpdate();

                if (row > 0) {
                    String successString = "Enrollment has been successfully inserted in the DB";
                    logger.info(successString);
                } else {
                    String failureString = "Failed to insert the Enrollment in the DB";
                    logger.error(failureString);
                    throw new Exception(failureString);
                }
                return true;
            } else {
                throw new Exception("Missing Fields for Enrollment");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
