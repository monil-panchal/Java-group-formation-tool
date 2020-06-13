package com.assessme.service;

/**
 * @author: monil
 * Created on: 2020-05-30
 */

import com.assessme.db.dao.EnrollmentDAO;
import com.assessme.db.dao.UserRoleDAOImpl;
import com.assessme.model.Enrollment;
import com.assessme.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User role service layer class for this application
 */
@Service
public class EnrollmentService {

    private Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    EnrollmentDAO enrollmentDAO;

    public EnrollmentService(EnrollmentDAO enrollmentDAO) {
        this.enrollmentDAO = enrollmentDAO;
    }

    public void insertEnrollment(Enrollment enrollment) throws Exception {
        try {
            logger.info("Inserting " + enrollment);
            enrollmentDAO.insertEnrollment(enrollment);
        }catch (Exception e){
            logger.error("couldn't insert "+ enrollment);
            e.printStackTrace();
            throw e;
        }
    }
}