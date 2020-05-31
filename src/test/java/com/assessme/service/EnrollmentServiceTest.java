package com.assessme.service;

import com.assessme.db.dao.EnrollmentDAO;
import com.assessme.db.dao.EnrollmentDAOImpl;
import com.assessme.db.dao.UserDAO;
import com.assessme.db.dao.UserDAOImpl;
import com.assessme.model.Enrollment;
import com.assessme.model.User;
import com.assessme.model.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:29 PM
 */
@SpringBootTest
public class EnrollmentServiceTest {
    private Logger logger = LoggerFactory.getLogger(EnrollmentServiceTest.class);

    @Mock
    private EnrollmentDAO enrollmentDAO;

    @Test
    void insertEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment(1L, 1,1L);
        Mockito.when(enrollmentDAO.insertEnrollment(enrollment)).thenReturn(true);
        assertTrue(enrollmentDAO.insertEnrollment(enrollment));
        verify(enrollmentDAO, times(1)).insertEnrollment(enrollment);
    }
}
