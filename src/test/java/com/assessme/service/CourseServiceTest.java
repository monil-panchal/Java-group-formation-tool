package com.assessme.service;

import com.assessme.db.dao.CourseDAOImpl;
import com.assessme.db.dao.UserDAOImpl;
import com.assessme.model.Course;
import com.assessme.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author: hardik
 * Created on: 2020-05-30
 */
@SpringBootTest
public class CourseServiceTest {

    private Logger logger = LoggerFactory.getLogger(CourseServiceTest.class);

    @Mock
    private CourseDAOImpl courseDAO;

    @Mock
    private UserDAOImpl userDAO;

    @Mock
    private Optional<Course> courseFromDB;

    @InjectMocks
    private CourseServiceImpl courseServiceMock;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private RoleServiceImpl roleService;

    // Unit test
    @Test
    public void getCourseWithCodeTest() throws Exception {
        logger.info("Running unit test for fetching course with course code");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Mockito.when(courseDAO.getCourseByCode(courseCode)).thenReturn(Optional.of(course));

        courseFromDB = courseServiceMock.getCourseWithCode(courseCode);

        Assert.isTrue(courseFromDB.isPresent(), "Course should not be empty");
        Assert.notNull(courseFromDB.get().getCourseCode(), "CourseCode should not be null");
        Assert.notNull(courseFromDB.get().getCourseName(), "courseName should not be null");
        Assertions.assertEquals(courseFromDB.get().getCourseName(), courseName);
        Assertions.assertEquals(courseFromDB.get().getCourseCode(), courseCode);
    }

    //Unit test
    @Test
    public void getCourseWithNameTest() throws Exception{
        logger.info("Running unit test for fetching course with course name");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Mockito.when(courseDAO.getCourseByName(courseName)).thenReturn(Optional.of(course));

        courseFromDB = courseServiceMock.getCourseWithCode(courseCode);

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

        Mockito.when(courseDAO.getAllCourse()).thenReturn(courseList);
        Assert.notEmpty(courseServiceMock.getCourseList().get(), "Course list is not null");
    }

    @Test
    public void removeCourseWithCourseCodeTest() throws Exception{
        logger.info("Running unit test for removing a course using courseCode");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Mockito.when(courseDAO.removeCourseByCourseCode(courseCode)).thenReturn(true);
        Assertions.assertEquals(courseServiceMock.removeCourseWithCourseCode(courseCode),true);
    }

    //Unit test
    @Test
    public void removeCourseWithCourseNameTest() throws Exception{
        logger.info("Running unit test for removing a course using courseCode");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Mockito.when(courseDAO.removeCourseByCourseName(courseName)).thenReturn(true);
        Assertions.assertEquals(courseServiceMock.removeCourseWithCourseName(courseName),true);
    }

    //Unit test
    @Test
    public void getCoursesByUserAndRoleTest() throws Exception{
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

        Mockito.when(userDAO.getUserByEmail(email)).thenReturn(Optional.of(user));
        Optional<User> userFromDB = userService.getUserFromEmail(email);
        Assert.isTrue(userFromDB.isPresent(), "User should not be empty");
        Assertions.assertEquals(userFromDB.get().getUserId(), userId);
        Assertions.assertEquals(userFromDB.get().getEmail(), email);

        int roleId = roleService.getRoleFromRoleName("STUDENT").get().getRoleId();;

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);
        List<Course> courseList = List.of(course);

        Mockito.when(courseDAO.listCourseByUserAndRole(userId,roleId)).thenReturn(Optional.of(courseList));
        Assert.notEmpty(courseServiceMock.getCourseList().get(), "Course list is not null");
    }

    //Unit test
    @Test
    public void addCourseTest() throws Exception{
        logger.info("Running unit test to add new Course");

        String courseCode = "CSCI5308";
        String courseName = "Advance Software Development Concepts";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);

        Optional<Course> optionalCourse = Optional.of(course);

        Mockito.when(courseDAO.addCourse(course)).thenReturn(Optional.of(course));
        Mockito.when(courseServiceMock.addCourse(course)).thenReturn(Optional.of(course));

        courseFromDB = courseServiceMock.addCourse(course);
        Assert.isTrue(courseFromDB.isPresent(), "Course should not be empty");
        Assert.notNull(courseFromDB.get().getCourseCode(), "CourseCode should not be null");
        Assert.notNull(courseFromDB.get().getCourseName(), "courseName should not be null");
        Assertions.assertEquals(courseFromDB.get().getCourseName(), courseName);
        Assertions.assertEquals(courseFromDB.get().getCourseCode(), courseCode);
    }

}
