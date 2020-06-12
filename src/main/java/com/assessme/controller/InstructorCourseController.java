package com.assessme.controller;

import com.assessme.model.*;
import com.assessme.service.*;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * @author Darshan Kathiriya
 * @created 12-June-2020 3:14 PM
 */
@Controller
public class InstructorCourseController {
    private Logger logger = LoggerFactory.getLogger(InstructorCourseController.class);

    StorageService storageService;
    UserService userService;
    CourseService courseService;
    EnrollmentService enrollmentService;
    MailSenderService mailSenderService;
    RoleService roleService;

    public InstructorCourseController(StorageService storageService, UserService userService,
                                      CourseService courseService, EnrollmentService enrollmentService,
                                      MailSenderService mailSenderService, RoleService roleService) {
        this.storageService = storageService;
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.mailSenderService = mailSenderService;
        this.roleService = roleService;
    }

    @PostMapping("/instructor_dashboard")
    public ModelAndView getDashboard(@RequestParam long userId) {
        ModelAndView mav = new ModelAndView("instructor_dashboard");
        logger.info(String.format("Getting Dashboard for Instructor %d", userId));
        try {
            int roleIdIns = roleService.getRoleFromRoleName("INSTRUCTOR").get().getRoleId();
            int roleIdTa = roleService.getRoleFromRoleName("TA").get().getRoleId();
            List<Course> courseList = courseService.getCoursesByUserAndRole(userId, roleIdTa).get();
            courseList.addAll(courseService.getCoursesByUserAndRole(userId, roleIdIns).get());
            mav.addObject("courses", courseList);
            mav.addObject("userId", userId);
        } catch (Exception e) {
            logger.error("Error Getting Courses");
            mav.addObject("message", "Error Fetching Courses");
        }
        return mav;
    }

    @PostMapping("/assign_ta_page/{courseCode}")
    public ModelAndView handleGET(
            @RequestParam long userId,
            @PathVariable String courseCode) {
        logger.info("Serving for course: " + courseCode);
        ModelAndView mav = new ModelAndView("assign_ta");
        try {
            Optional<List<User>> userList = userService.getUsersNotAssignedForCourse(courseCode, "TA");
            mav.addObject("course_code", courseCode);
            mav.addObject("users", userList.get());
            mav.addObject("userId", userId);
        } catch (Exception e) {
            mav.addObject("message", "Error Fetching Users");
        }
        return mav;
    }

    @PostMapping("/assign_ta/{courseCode}")
    public ResponseEntity<ResponseDTO> handleAssignTA(@RequestParam String userEmail,
                                         @RequestParam long currentUserId,
                                         @PathVariable String courseCode,
                                         RedirectAttributes redirectAttributes) {
        logger.info(String.format("assigning %s as TA for course: %s", userEmail, courseCode));
        String roleName = "TA";
        HttpStatus httpStatus = null;
        ResponseDTO<Long> responseDTO = null;
        try {
            Optional<User> user = userService.getUserFromEmail(userEmail);
            long courseId = courseService.getCourseWithCode(courseCode).get().getCourseId();
            Optional<Role> taRole = roleService.getRoleFromRoleName(roleName);
            userService.updateUserRole(user.get(), roleName);
            Enrollment enrollment = new Enrollment(user.get().getUserId(),
                    taRole.get().getRoleId(), courseId);
            enrollmentService.insertEnrollment(enrollment);
//            redirectAttributes.addFlashAttribute("message",
//                    String.format("TA has been assigned for course %s successfully.", courseCode));
//            redirectAttributes.addFlashAttribute("isSuccess", true);
            responseDTO = new ResponseDTO(true, null, null, currentUserId);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("message",
//                    "Error while accessing database");
//            redirectAttributes.addFlashAttribute("isSuccess", false);
            responseDTO = new ResponseDTO(false, null, null, currentUserId);
            httpStatus = HttpStatus.OK;
        }
//        redirectAttributes.addFlashAttribute("currentUserId", currentUserId);
        return new ResponseEntity(responseDTO, httpStatus);
    }

    @PostMapping("/upload_page/{courseCode}")
    public ModelAndView csvFileUploadForm(
            @RequestParam long userId,
            @PathVariable String courseCode) {
        ModelAndView mav = new ModelAndView("csvupload");
        logger.info("Serving for course: " + courseCode);
        mav.addObject("course_code", courseCode);
        mav.addObject("userId", userId);
        return mav;
    }


    @PostMapping("/csvupload/{courseCode}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam long instructorId,
                                   @PathVariable String courseCode,
                                   RedirectAttributes redirectAttributes) {
        String roleName = "STUDENT";
        try {
            Optional<Role> studentRole = roleService.getRoleFromRoleName(roleName);
            for (String[] csvRow : storageService.storeAndParseAll(file)) {
                logger.info(String.format("UserEmail: %s", csvRow[3]));
                long userId;
                try {
                    User userEntity = userService.getUserFromEmail(csvRow[3]).get();
                    logger.info("User: " + userEntity);
                    userId = userEntity.getUserId();
                    userService.updateUserRole(userEntity, roleName);
                } catch (Exception e) {
                    User newUser = new User();
                    newUser.setBannerId(csvRow[0]);
                    newUser.setLastName(csvRow[1]);
                    newUser.setFirstName(csvRow[2]);
                    newUser.setEmail(csvRow[3]);
                    User userEntity = userService.addUser(newUser, roleName).get();
                    userId = userEntity.getUserId();
                    mailSenderService.sendSimpleMessage(userEntity.getEmail(),
                            "Your Account Has Been Created",
                            "Your password is YourBannerId_YourLastName");
                }
                Enrollment enrollment = new Enrollment(userId, studentRole.get().getRoleId(),
                        (long) courseService.getCourseWithCode(courseCode).get().getCourseId()
                );
                enrollmentService.insertEnrollment(enrollment);
            }
            redirectAttributes.addFlashAttribute("message",
                    "Users has been successfully created!");
            redirectAttributes.addFlashAttribute("isSuccess", true);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message",
                    "empty file was selected!");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        } catch (CsvException e) {
            redirectAttributes.addFlashAttribute("message",
                    "Error Parsing CSV File");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Error while accessing database");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        redirectAttributes.addFlashAttribute("instructorId", instructorId);
        return String.format("forward:/upload_page/%s", courseCode);
    }
}
