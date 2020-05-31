package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.Enrollment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:42 PM
 */
@Repository
public class EnrollmentDAOImpl implements EnrollmentDAO {
    private static EnrollmentDAOImpl instance;

    private Logger logger = LoggerFactory.getLogger(EnrollmentDAOImpl.class);

    @Autowired
    private DBConnectionBuilder dbConnectionBuilder;

    private Optional<Connection> connection;

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
        connection = dbConnectionBuilder.createDBConnection();
        try {
            if (enrollment.getUserId() != null && enrollment.getCourseId() != null && enrollment.getRoleId() != null) {
                String insertUserSQLQuery = "INSERT INTO user_course_role values (?,?,?)";
                PreparedStatement preparedStatement = connection.get().prepareStatement(insertUserSQLQuery, Statement.RETURN_GENERATED_KEYS);

                //Setting the query params
                preparedStatement.setLong(1, enrollment.getUserId());
                preparedStatement.setLong(2, enrollment.getCourseId());
                preparedStatement.setInt(3, enrollment.getRoleId());

                // Executing the query to store the user role record
                int row = preparedStatement.executeUpdate();

                // check if the record was inserted successfully
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
