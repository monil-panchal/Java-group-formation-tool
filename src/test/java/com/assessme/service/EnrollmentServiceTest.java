package com.assessme.service;

<<<<<<< HEAD

import com.assessme.model.Enrollment;
import com.assessme.model.Role;
import com.assessme.db.dao.EnrollmentDAO;
import com.assessme.model.User;
=======
import com.assessme.db.dao.*;
import com.assessme.model.Enrollment;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.model.UserRole;
>>>>>>> bb2fedea8ae20acf521b79cc3ae430cd8b05843a
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
<<<<<<< HEAD

import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jmx.export.annotation.ManagedOperation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
>>>>>>> bb2fedea8ae20acf521b79cc3ae430cd8b05843a

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:29 PM
 */
@SpringBootTest
public class EnrollmentServiceTest {
    private Logger logger = LoggerFactory.getLogger(EnrollmentServiceTest.class);

    @Mock
    private UserService userService;

    @Mock
    private EnrollmentDAO enrollmentDAO;

    @Mock
    private RoleService roleService;

    @Test
    void insertEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment(1L, 1,1L);
        when(enrollmentDAO.insertEnrollment(enrollment)).thenReturn(true);
        assertTrue(enrollmentDAO.insertEnrollment(enrollment));
        verify(enrollmentDAO, times(1)).insertEnrollment(enrollment);
    }

    @Test
    void integrateUserRoleCourse() throws Exception {
        User user = Mockito.mock(User.class);
        when(userService.getUserFromEmail("jane.doe@dal.ca")).thenReturn(Optional.of(user));
        long userId = userService.getUserFromEmail("jane.doe@dal.ca").get().getUserId();
        long courseId = 1L;
        Mockito.when(roleService.getRoleFromRoleName("STUDENT")).thenReturn(Optional.of(new Role(1, "STUDENT")));
        int roleId = roleService.getRoleFromRoleName("STUDENT").get().getRoleId();

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        when(enrollmentDAO.insertEnrollment(enrollment)).thenReturn(true);
        assertTrue(enrollmentDAO.insertEnrollment(enrollment));
        verify(enrollmentDAO, times(1)).insertEnrollment(enrollment);
    }
<<<<<<< HEAD

=======
>>>>>>> bb2fedea8ae20acf521b79cc3ae430cd8b05843a
}
