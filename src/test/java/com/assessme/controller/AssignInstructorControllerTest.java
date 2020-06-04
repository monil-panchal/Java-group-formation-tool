package com.assessme.controller;

import com.assessme.model.Course;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.service.*;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Darshan Kathiriya
 * @created 02-June-2020 7:28 PM
 */

@ExtendWith(MockitoExtension.class)
class AssignInstructorControllerTest {
    private Logger logger = LoggerFactory.getLogger(AssignInstructorControllerTest.class);
    private MockMvc mockMvc;

    @Mock
    UserService userService;
    @Mock
    EnrollmentService enrollmentService;
    @Mock
    CourseService courseService;
    @Mock
    MailSenderService mailSenderService;
    @Mock
    RoleService roleService;
    @Mock
    RedirectAttributes redirectAttributes;

    @Mock
    CSVReader reader;

    @InjectMocks
    AssignInstructorController controller;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .addFilters()
                .build();
    }

    @Test
    void handleAssignInstuctor() throws Exception {
        User user = new User();
        user.setUserId(1L);
        when(userService.getUserFromEmail("email.com")).thenReturn(Optional.of(user));

        Course course = new Course();
        course.setCourseId(1);
        when(courseService.getCourseWithCode("CSCI_TEST")).thenReturn(Optional.of(course));

        when(roleService.getRoleFromRoleName("INSTRUCTOR"))
                .thenReturn(Optional.of(new Role(1, "INSTRUCTOR")));

        mockMvc.perform(post("/assign_instructor/CSCI_TEST")
                .contentType(MediaType.APPLICATION_JSON)
                .param("user_email", "email.com"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/assign_instructor/CSCI_TEST"));
    }
}