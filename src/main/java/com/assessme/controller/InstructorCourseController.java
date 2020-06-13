package com.assessme.controller;

import com.assessme.auth.CurrentUserService;
import com.assessme.model.Course;
import com.assessme.model.Enrollment;
import com.assessme.model.ResponseDTO;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.service.CourseService;
import com.assessme.service.EnrollmentService;
import com.assessme.service.MailSenderService;
import com.assessme.service.RoleService;
import com.assessme.service.StorageService;
import com.assessme.service.UserService;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Darshan Kathiriya
 * @created 12-June-2020 3:14 PM
 */
@Controller
public class InstructorCourseController {

  StorageService storageService;
  UserService userService;
  CourseService courseService;
  EnrollmentService enrollmentService;
  MailSenderService mailSenderService;
  RoleService roleService;
  CurrentUserService currentUserService;
  private Logger logger = LoggerFactory.getLogger(InstructorCourseController.class);

  public InstructorCourseController(StorageService storageService, UserService userService,
      CourseService courseService, EnrollmentService enrollmentService,
      MailSenderService mailSenderService, RoleService roleService,
      CurrentUserService currentUserService) {
    this.storageService = storageService;
    this.userService = userService;
    this.courseService = courseService;
    this.enrollmentService = enrollmentService;
    this.mailSenderService = mailSenderService;
    this.roleService = roleService;
    this.currentUserService = currentUserService;
  }

  @GetMapping("/instructor_dashboard")
  public ModelAndView getDashboard() {
    logger.info("Serving Instructor Dashboard");
    ModelAndView mav = new ModelAndView("instructor_dashboard");
    try {
      long userId = currentUserService.getAuthenticatedUser().get().getUserId();
      logger.info(String.format("Getting Course Instructor Dashboard for User %d", userId));
      int roleIdIns = roleService.getRoleFromRoleName("INSTRUCTOR").get().getRoleId();
      int roleIdTa = roleService.getRoleFromRoleName("TA").get().getRoleId();
      List<Course> courseList = courseService.getCoursesByUserAndRole(userId, roleIdTa).get();
      courseList.addAll(courseService.getCoursesByUserAndRole(userId, roleIdIns).get());
      mav.addObject("courses", courseList);
    } catch (Exception e) {
      logger.error("Error Getting Courses");
      mav.addObject("message", "Error Fetching Courses");
    }
    return mav;
  }

  @GetMapping("/assign_ta_page/{courseCode}")
  public ModelAndView handleGET(
      @PathVariable String courseCode) {
    logger.info("Serving for course: " + courseCode);
    ModelAndView mav = new ModelAndView("assign_ta");
    try {
      Optional<List<User>> userList = userService.getUsersNotAssignedForCourse(courseCode);
      mav.addObject("course_code", courseCode);
      mav.addObject("users", userList.get());
    } catch (Exception e) {
      logger.error("Error fetching assign_ta_page");
      mav.addObject("message", "Error Fetching Users");
    }
    return mav;
  }

  @PostMapping("/assign_ta/{courseCode}")
  public ResponseEntity<ResponseDTO> handleAssignTA(@RequestParam String userEmail,
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
      responseDTO = new ResponseDTO(true, null, null, null);
      httpStatus = HttpStatus.OK;
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
//            redirectAttributes.addFlashAttribute("message",
//                    "Error while accessing database");
//            redirectAttributes.addFlashAttribute("isSuccess", false);
      responseDTO = new ResponseDTO(false, null, null, null);
      httpStatus = HttpStatus.OK;
    }
//        redirectAttributes.addFlashAttribute("currentUserId", currentUserId);
    return new ResponseEntity(responseDTO, httpStatus);
  }

  @GetMapping("/csvupload/{courseCode}")
  public ModelAndView csvFileUploadForm(
      @PathVariable String courseCode) {
    ModelAndView mav = new ModelAndView("csvupload");
    logger.info("Serving for course: " + courseCode);
    mav.addObject("course_code", courseCode);
    return mav;
  }

  @PostMapping("/csvupload/{courseCode}")
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
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
    return String.format("redirect:/csvupload/%s", courseCode);
  }
}
