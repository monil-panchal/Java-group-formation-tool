package com.assessme.controller;

import com.assessme.model.Course;
import com.assessme.model.Role;
import com.assessme.service.CourseService;
import com.assessme.service.RoleService;
import com.assessme.service.UserService;
import org.apache.tomcat.util.digester.Rule;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Darshan Kathiriya
 * @created 04-June-2020 2:54 PM
 */
@ExtendWith(MockitoExtension.class)
class InstructorDashboardControllerTest {
    private Logger logger = LoggerFactory.getLogger(InstructorDashboardControllerTest.class);
    private MockMvc mockMvc;

    @Mock
    CourseService courseService;

    @Mock
    RoleService roleService;

    @InjectMocks
    InstructorDashboardController controller;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .addFilters()
                .build();
    }

    @Test
    void getDashboard() throws Exception {
        when(roleService.getRoleFromRoleName("INSTRUCTOR"))
                .thenReturn(Optional.of(new Role(1, "INSTRUCTOR")));
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("CSCI_TEST","TEST Course"));
        assertTrue(roleService.getRoleFromRoleName("INSTRUCTOR").isPresent());
        when(courseService.getCoursesByUserAndRole(1L, 1))
                .thenReturn(Optional.of(courseList));
        assertTrue(courseService.getCoursesByUserAndRole(1L, 1).isPresent());

    }
}