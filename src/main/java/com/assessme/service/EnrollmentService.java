package com.assessme.service;

import com.assessme.model.Enrollment;

/**
 * @author Darshan Kathiriya
 * @created 13-June-2020 7:55 PM
 */
public interface EnrollmentService {

    void insertEnrollment(Enrollment enrollment) throws Exception;

    void insertEnrollment(Long userId, Integer roleId, long courseId) throws Exception;
}
