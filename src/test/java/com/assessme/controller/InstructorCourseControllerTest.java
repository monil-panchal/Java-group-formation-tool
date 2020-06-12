package com.assessme.controller;

import com.assessme.model.*;
import com.assessme.service.*;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Darshan Kathiriya
 * @created 12-June-2020 3:18 PM
 */
@ExtendWith(MockitoExtension.class)
class InstructorCourseControllerTest {
    private Logger logger = LoggerFactory.getLogger(InstructorCourseControllerTest.class);
    private MockMvc mockMvc;

    @Mock
    UserService userService;
    @Mock
    StorageService storageService;
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

    @InjectMocks
    InstructorCourseController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .addFilters()
                .build();
    }

    @Test
    void handleFileUpload() throws Exception {
        MockMultipartFile fooFile = new MockMultipartFile("file", "foo.csv",
                MediaType.TEXT_PLAIN_VALUE, "B001,Hello,World,email.com".getBytes());

        List<String[]> userList = new ArrayList<>();
        userList.add(new String[]{"B001", "Hello", "World", "email.com"});

        when(storageService.storeAndParseAll(fooFile)).thenReturn(userList);
        assertTrue(!storageService.storeAndParseAll(fooFile).isEmpty());
        when(roleService.getRoleFromRoleName("STUDENT")).thenReturn(Optional.of(new Role(1,"STUDENT")));
        assertTrue(roleService.getRoleFromRoleName("STUDENT").isPresent());

        User user = new User();
        user.setUserId(1L);

        when(userService.getUserFromEmail("email.com")).thenReturn(Optional.of(user));
        assertTrue(userService.getUserFromEmail("email.com").isPresent());
        Course course = new Course();
        course.setCourseId(1);
        when(courseService.getCourseWithCode("CSCI_TEST")).thenReturn(Optional.of(course));
        assertTrue(courseService.getCourseWithCode("CSCI_TEST").isPresent());


        mockMvc.perform(multipart("/csvupload/CSCI_TEST")
                .file(fooFile)
                .requestAttr("instructorId", 1L)
        ).andDo(print());
//                .andExpect(status().isFound())
//                .andExpect(header().string("Location", "/csvupload/CSCI_TEST"));
    }
    @Test
    void handleBadRequestAssignTA() throws Exception{
        mockMvc.perform(get("/assign_ta"))
        .andExpect(status().isNotFound());
    }

    @Test
    void handleGetAssignTAPage() throws Exception{
        mockMvc.perform(post("/assign_ta_page/CSCI_TEST")
        .contentType(MediaType.APPLICATION_JSON)
        .param("userId", "1")
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
                .param("userEmail", "email.com")
                .param("currentUserId", "1")
        )
                .andExpect(status().isOk());
    }
    @Test
    void fetchInstructorDashboard() throws Exception {
        when(roleService.getRoleFromRoleName("INSTRUCTOR"))
                .thenReturn(Optional.of(new Role(2, "INSTRUCTOR")));
        when(roleService.getRoleFromRoleName("TA"))
                .thenReturn(Optional.of(new Role(3, "TA")));
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("CSCI_TEST", "TEST Course"));

        when(courseService.getCoursesByUserAndRole(1L, 2))
                .thenReturn(Optional.of(courseList));
        when(courseService.getCoursesByUserAndRole(1L, 3))
                .thenReturn(Optional.of(courseList));

        mockMvc.perform(post("/instructor_dashboard")
                .param("userId", "1")
        )
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("userId"))
                .andExpect(model().attributeDoesNotExist("message"))
        ;
    }
}