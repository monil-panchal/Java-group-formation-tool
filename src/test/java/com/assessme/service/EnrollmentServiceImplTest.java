package com.assessme.service;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.assessme.db.dao.EnrollmentDAO;
import com.assessme.model.Enrollment;
import com.assessme.model.Role;
import com.assessme.model.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:29 PM
 */
@SpringBootTest
public class EnrollmentServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImplTest.class);

    @Mock
    private UserService userService;

    @Mock
    private EnrollmentDAO enrollmentDAO;

    @Mock
    private RoleService roleService;

    @Test
    void insertEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment(1L, 1, 1L);
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
        Mockito.when(roleService.getRoleFromRoleName("STUDENT"))
            .thenReturn(Optional.of(new Role(1, "STUDENT")));
        int roleId = roleService.getRoleFromRoleName("STUDENT").get().getRoleId();

        Enrollment enrollment = new Enrollment(userId, roleId, courseId);
        when(enrollmentDAO.insertEnrollment(enrollment)).thenReturn(true);
        assertTrue(enrollmentDAO.insertEnrollment(enrollment));
        verify(enrollmentDAO, times(1)).insertEnrollment(enrollment);
    }
}
