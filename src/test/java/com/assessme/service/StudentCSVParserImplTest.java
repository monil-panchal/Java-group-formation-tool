package com.assessme.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.assessme.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

/**
 * @author Darshan Kathiriya
 * @created 15-June-2020 6:07 PM
 */
@ExtendWith(MockitoExtension.class)
class StudentCSVParserImplTest {

    @Mock
    StudentCSVParser studentCSVParser;

    @Test
    void parseCSV() {
        MockMultipartFile fooFile = new MockMultipartFile("file", "foo.csv",
            MediaType.TEXT_PLAIN_VALUE, "B001,Hello,World,email.com".getBytes());
        List<User> userList = new ArrayList<>();
        User u = new User();
        u.setBannerId("B001");
        u.setFirstName("Hello");
        u.setLastName("World");
        u.setEmail("email.com");
        userList.add(u);
        when(studentCSVParser.parseStudents(new ArrayList<String>()))
            .thenReturn(Optional.of(userList));
        assertTrue(studentCSVParser.parseStudents(new ArrayList<String>()).isPresent());
    }

}