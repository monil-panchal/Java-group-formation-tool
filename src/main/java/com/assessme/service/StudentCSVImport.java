package com.assessme.service;

import com.assessme.model.Role;
import com.assessme.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Darshan Kathiriya
 * @created 15-June-2020 3:59 PM
 */
@Service
public class StudentCSVImport {

    private static StudentCSVImport instance;
    final String ROLENAME = "STUDENT";
    private final Logger logger = LoggerFactory.getLogger(StudentCSVImport.class);
    UserService userService;
    RoleService roleService;
    EnrollmentService enrollmentService;
    CourseService courseService;
    MailSenderService mailSenderService;
    List<String> successResults = new ArrayList<>();
    List<String> failureResults = new ArrayList<>();

    public StudentCSVImport() {
        this.userService = UserServiceImpl.getInstance();
        this.roleService = RoleServiceImpl.getInstance();
        this.enrollmentService = EnrollmentServiceImpl.getInstance();
        this.courseService = CourseServiceImpl.getInstance();
        this.mailSenderService = MailSenderServiceImpl.getInstance();
    }

    public static StudentCSVImport getInstance() {
        if (instance == null) {
            instance = new StudentCSVImport();
        }
        return instance;
    }

    public void importStudents(StudentCSVParser parser, String courseCode) {
        try {
            List<User> studentList;
            try {
                studentList = parser.parseStudents(failureResults).get();
            } catch (Exception e) {
                return;
            }
            Optional<Role> studentRole = roleService.getRoleFromRoleName(ROLENAME);
            for (User u : studentList) {
                User userToBeEnrolled;
                try {
                    userToBeEnrolled = userService.getUserFromEmail(u.getEmail()).get();
                    logger.info("User: " + userToBeEnrolled);
                    userService.updateUserRole(userToBeEnrolled, ROLENAME);
                    successResults.add("Updated: " + userToBeEnrolled);
                } catch (Exception e) {
                    userToBeEnrolled = userService.addUser(u, ROLENAME).get();
                    mailSenderService.sendSimpleMessage(userToBeEnrolled.getEmail(),
                        "Your Account Has Been Created",
                        "Your password is YourBannerId_YourLastName");
                }
                try {

                    enrollmentService.insertEnrollment(
                        userToBeEnrolled.getUserId(),
                        studentRole.get().getRoleId(),
                        courseService.getCourseWithCode(courseCode).get().getCourseId()
                    );
                    successResults.add("Enrolled: " + userToBeEnrolled);
                } catch (Exception e) {
                    failureResults.add("Failed to Enroll: " + userToBeEnrolled);
                }
            }
        } catch (Exception e) {
            failureResults.add("Failed to Enroll all Students: " + e.getMessage());
        }
    }

    public List<String> getSuccessResults() {
        return successResults;
    }

    public List<String> getFailureResults() {
        return failureResults;
    }
}
