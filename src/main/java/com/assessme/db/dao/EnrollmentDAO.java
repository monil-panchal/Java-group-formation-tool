package com.assessme.db.dao;

import com.assessme.model.Enrollment;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:47 PM
 */
public interface EnrollmentDAO {

    Enrollment getEnrollment(long userId, int roleId, long courseId);

    boolean insertEnrollment(Enrollment enrollment) throws Exception;
}
