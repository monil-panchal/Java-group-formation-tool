package com.assessme.service;

import com.assessme.db.dao.CourseDAOImpl;
import com.assessme.db.dao.RoleDAOImpl;
import com.assessme.db.dao.UserDAOImpl;
import com.assessme.model.Course;
import com.assessme.model.Role;
import com.assessme.model.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author: hardik Created on: 2020-05-30
 */
@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    private final Logger logger = LoggerFactory.getLogger(CourseServiceTest.class);

    @Mock
    private CourseDAOImpl courseDAO;

    @Mock
    private UserDAOImpl userDAO;

    private Optional<Role> roleFromDB;

    private Optional<Course> courseFromDB;

    @Mock
    private CourseServiceImpl courseServiceMock;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private RoleDAOImpl roleDAO;

    @Test
    public void getCourseWithCodeTest() throws Exception {
        logger.info("Running unit test for fetching course with course code");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Mockito.when(courseServiceMock.getCourseWithCode(courseCode))
            .thenReturn(Optional.of(course));

        courseFromDB = courseServiceMock.getCourseWithCode(courseCode);

        Assert.isTrue(courseFromDB.isPresent(), "Course should not be empty");
        Assert.notNull(courseFromDB.get().getCourseCode(), "CourseCode should not be null");
        Assert.notNull(courseFromDB.get().getCourseName(), "courseName should not be null");
        Assertions.assertEquals(courseFromDB.get().getCourseName(), courseName);
        Assertions.assertEquals(courseFromDB.get().getCourseCode(), courseCode);
    }

    //Unit test
    @Test
    public void getCourseWithNameTest() throws Exception {
        logger.info("Running unit test for fetching course with course name");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Mockito.when(courseServiceMock.getCourseWithName(courseName))
            .thenReturn(Optional.of(course));

        courseFromDB = courseServiceMock.getCourseWithName(courseName);

        Assert.isTrue(courseFromDB.isPresent(), "Course should not be empty");
        Assert.notNull(courseFromDB.get().getCourseCode(), "CourseCode should not be null");
        Assert.notNull(courseFromDB.get().getCourseName(), "courseName should not be null");
        Assertions.assertEquals(courseFromDB.get().getCourseName(), courseName);
        Assertions.assertEquals(courseFromDB.get().getCourseCode(), courseCode);
    }

    //Unit test
    @Test
    public void getCourseListTest() throws Exception {
        logger.info("Running unit test for fetching all the courses");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        List<Course> courseList = List.of(course);

        Mockito.when(courseServiceMock.getCourseList()).thenReturn(Optional.of(courseList));
        Assert.notEmpty(courseServiceMock.getCourseList().get(), "Course list is not null");
    }

    @Test
    public void removeCourseWithCourseCodeTest() throws Exception {
        logger.info("Running unit test for removing a course using courseCode");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Mockito.when(courseServiceMock.removeCourseWithCourseCode(courseCode)).thenReturn(true);
        Assertions.assertEquals(courseServiceMock.removeCourseWithCourseCode(courseCode), true);
    }

    //Unit test
    @Test
    public void removeCourseWithCourseNameTest() throws Exception {
        logger.info("Running unit test for removing a course using courseCode");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Mockito.when(courseServiceMock.removeCourseWithCourseName(courseName)).thenReturn(true);
        Assertions.assertEquals(courseServiceMock.removeCourseWithCourseName(courseName), true);
    }

    //Unit test
    @Test
    public void getCoursesByUserAndRoleTest() throws Exception {
        logger.info("Running unit test for fetching enrolled course with userID and roleID");

        String email = "testUser@email.com";
        Long userId = 1L;
        User user = new User();
        user.setBannerId("B00838558");
        user.setActive(true);
        user.setFirstName("John");
        user.setLastName("Abraham");
        user.setUserId(userId);
        user.setEmail(email);

        Mockito.when(userService.getUserFromEmail(email)).thenReturn(Optional.of(user));
        Optional<User> userFromDB = userService.getUserFromEmail(email);
        Assert.isTrue(userFromDB.isPresent(), "User should not be empty");
        Assertions.assertEquals(userFromDB.get().getUserId(), userId);
        Assertions.assertEquals(userFromDB.get().getEmail(), email);

        Mockito.when(roleDAO.getRolebyName("STUDENT")).
            thenReturn(Optional.of(new Role(4, "STUDENT")));
        roleFromDB = roleDAO.getRolebyName("STUDENT");
        int roleId = roleFromDB.get().getRoleId();

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);
        List<Course> courseList = List.of(course);

        Mockito.when(courseServiceMock.getCoursesByUserAndRole(userId, roleId))
            .thenReturn(Optional.of(courseList));
        Assert.notEmpty(courseServiceMock.getCoursesByUserAndRole(userId, roleId).get(),
            "Course list is not null");
    }

    //Unit test
    @Test
    public void addCourseTest() throws Exception {
        logger.info("Running unit test to add new Course");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Optional<Course> optionalCourse = Optional.of(course);

        Mockito.when(courseServiceMock.addCourse(course)).thenReturn(Optional.of(course));
        Mockito.when(courseServiceMock.addCourse(course)).thenReturn(Optional.of(course));

        courseFromDB = courseServiceMock.addCourse(course);
        Assert.isTrue(courseFromDB.isPresent(), "Course should not be empty");
        Assert.notNull(courseFromDB.get().getCourseCode(), "CourseCode should not be null");
        Assert.notNull(courseFromDB.get().getCourseName(), "courseName should not be null");
        Assertions.assertEquals(courseFromDB.get().getCourseName(), courseName);
        Assertions.assertEquals(courseFromDB.get().getCourseCode(), courseCode);
    }

}
