package com.assessme.service;

/**
 * @author: monil
 * Created on: 2020-05-30
 */

import com.assessme.db.dao.EnrollmentDAO;
import com.assessme.model.Enrollment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * User role service layer class for this application
 */
@Service
public class EnrollmentServiceImpl implements EnrollmentService{

    private Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    EnrollmentDAO enrollmentDAO;

    public EnrollmentServiceImpl(EnrollmentDAO enrollmentDAO) {
        this.enrollmentDAO = enrollmentDAO;
    }

    @Override
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