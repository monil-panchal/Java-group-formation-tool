package com.assessme.service;

/**
 * @author: monil Created on: 2020-05-30
 */

import com.assessme.db.dao.EnrollmentDAO;
import com.assessme.db.dao.EnrollmentDAOImpl;
import com.assessme.model.Enrollment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * User role service layer class for this application
 */
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private static EnrollmentServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    EnrollmentDAO enrollmentDAO;

    public EnrollmentServiceImpl() {
        this.enrollmentDAO = EnrollmentDAOImpl.getInstance();
    }

    public static EnrollmentServiceImpl getInstance() {
        if (instance == null) {
            instance = new EnrollmentServiceImpl();
        }
        return instance;
    }

    @Override
    public void insertEnrollment(Enrollment enrollment) throws Exception {
        try {
            logger.info("Inserting " + enrollment);
            enrollmentDAO.insertEnrollment(enrollment);
        } catch (Exception e) {
            logger.error("couldn't insert " + enrollment);
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void insertEnrollment(Long userId, Integer roleId, long courseId) throws Exception {
        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        this.insertEnrollment(enrollment);
    }
}