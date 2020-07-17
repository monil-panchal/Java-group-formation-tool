package com.assessme.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: hardik Created on: 2020-06-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SuppressWarnings("deprecation")
public class EnrollmentTest {

    private final Logger logger = LoggerFactory.getLogger(EnrollmentTest.class);

    @Test
    public void ConstructorTests() {
        logger.info("Running unit test for enrollment constructor");

        Long userId = 1L;
        Integer roleId = 1;
        Long courseId = 1L;

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);

        Assert.isTrue(enrollment.getUserId().equals(userId));
        Assert.isTrue(enrollment.getRoleId().equals(roleId));
        Assert.isTrue(enrollment.getCourseId().equals(courseId));
    }

    public void getUserIdTest() {
        logger.info("Running unit test for fetching user Id from enrollment");

        Long userId = 1L;
        Integer roleId = 1;
        Long courseId = 1L;

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        enrollment.setUserId(2L);
        Assert.isTrue(enrollment.getUserId().equals(2L));
    }

    public void setUserIdTest() {
        logger.info("Running unit test for setting user Id from enrollment");

        Long userId = 1L;
        Integer roleId = 1;
        Long courseId = 1L;

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        enrollment.setUserId(2L);

        Assert.isTrue(enrollment.getUserId().equals(2L));
    }

    public void getRoleIdTest() {
        logger.info("Running unit test for getting roleId from enrollment");

        Long userId = 1L;
        Integer roleId = 1;
        Long courseId = 1L;

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        enrollment.setRoleId(2);

        Assert.isTrue(enrollment.getRoleId().equals(2));

    }

    public void setRoleIdTest() {
        logger.info("Running unit test for setting roleId from enrollment");

        Long userId = 1L;
        Integer roleId = 1;
        Long courseId = 1L;

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        enrollment.setRoleId(2);

        Assert.isTrue(enrollment.getRoleId().equals(2));
    }

    public void getCourseIdTest() {
        logger.info("Running unit test for getting courseId from enrollment");

        Long userId = 1L;
        Integer roleId = 1;
        Long courseId = 1L;

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        enrollment.setCourseId(2L);

        Assert.isTrue(enrollment.getCourseId().equals(2L));
    }

    public void setCourseIdTest() {
        logger.info("Running unit test for setting courseId from enrollment");

        Long userId = 1L;
        Integer roleId = 1;
        Long courseId = 1L;

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        enrollment.setCourseId(2L);

        Assert.isTrue(enrollment.getCourseId().equals(2L));
    }
}
