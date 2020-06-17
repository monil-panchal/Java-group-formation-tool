package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.Enrollment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import com.assessme.db.CallStoredProcedure;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:42 PM
 */
@Repository
public class EnrollmentDAOImpl implements EnrollmentDAO {
    private Logger logger = LoggerFactory.getLogger(EnrollmentDAOImpl.class);


    private DBConnectionBuilder dbConnectionBuilder;

    private Optional<Connection> connection;

    public EnrollmentDAOImpl(DBConnectionBuilder dbConnectionBuilder) {
        this.dbConnectionBuilder = dbConnectionBuilder;
    }

    @Override
    public Enrollment getEnrollment(long userId, int roleId, long courseId) {
        return null;
    }

    @Override
    public boolean insertEnrollment(Enrollment enrollment) throws Exception {
        CallStoredProcedure procedure = null;
        try {
            if (enrollment.getUserId() != null && enrollment.getCourseId() != null && enrollment.getRoleId() != null) {

                procedure = new CallStoredProcedure(dbConnectionBuilder, "spAddUserCourseRole(?,?,?)");
                procedure.setParameter(1, enrollment.getCourseId());
                procedure.setParameter(2, enrollment.getCourseId());
                procedure.setParameter(3, enrollment.getRoleId());

                int row = procedure.executeUpdate();

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
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());

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
