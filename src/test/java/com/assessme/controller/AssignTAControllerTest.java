package com.assessme.controller;

import com.assessme.model.*;
import com.assessme.service.*;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Darshan Kathiriya
 * @created 02-June-2020 7:28 PM
 */

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignTAControllerTest {
    private Logger logger = LoggerFactory.getLogger(AssignTAControllerTest.class);
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
    AssignTAController controller;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .addFilters()
                .build();
    }

    @Test
    void handleBadRequest() throws Exception{
        mockMvc.perform(get("/assign_ta"))
        .andExpect(status().isNotFound());
    }

    @Test
    void handleGet() throws Exception{
        mockMvc.perform(post("/assign_ta_page/CSCI_TEST")
        .contentType(MediaType.APPLICATION_JSON)
        .param("instructorId", "1")
        )
        .andExpect(status().isOk());
    }

    @Test
    void handleAssignTA() throws Exception {
        User user = new User();
        user.setUserId(1L);
        when(userService.getUserFromEmail("email.com")).thenReturn(Optional.of(user));

        Course course = new Course();
        course.setCourseId(1);
        when(courseService.getCourseWithCode("CSCI_TEST")).thenReturn(Optional.of(course));

        when(roleService.getRoleFromRoleName("TA")).thenReturn(Optional.of(new Role(1, "TA")));

        mockMvc.perform(post("/assign_ta/CSCI_TEST")
                .contentType(MediaType.APPLICATION_JSON)
                .param("user_email", "email.com")
                .param("instructorId", "1")
        )
                .andExpect(status().isOk());
    }
}