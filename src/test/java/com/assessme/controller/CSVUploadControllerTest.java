package com.assessme.controller;

import com.assessme.model.*;
import com.assessme.service.*;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
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
class CSVUploadControllerTest {
    private Logger logger = LoggerFactory.getLogger(CSVUploadControllerTest.class);
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

    @Mock
    CSVReader reader;

    @InjectMocks
    CSVUploadController controller;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
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
}