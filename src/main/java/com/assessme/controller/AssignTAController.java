package com.assessme.controller;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 12:28 AM
 */

import com.assessme.db.dao.EnrollmentDAO;
import com.assessme.db.dao.EnrollmentDAOImpl;
import com.assessme.model.Enrollment;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.service.*;
import com.assessme.service.CourseService;
import com.assessme.util.AppConstant;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class AssignTAController {

    private Logger logger = LoggerFactory.getLogger(AssignTAController.class);

    UserService userService;
    CourseService courseService;
    EnrollmentService enrollmentService;
    MailSenderService mailSenderService;
    RoleService roleService;

    public AssignTAController(UserService userService, CourseService courseService,
                              EnrollmentService enrollmentService, MailSenderService mailSenderService,
                              RoleService roleService) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.mailSenderService = mailSenderService;
        this.roleService = roleService;
    }


    @GetMapping("/assign_ta/{courseCode}")
    public ModelAndView handleGET(
            @PathVariable String courseCode) {
        logger.info("Serving for course: " + courseCode);
        ModelAndView mav = new ModelAndView("assign_ta");
        try {
            Optional<List<User>> userList = userService.getUserList();
            mav.addObject("course_code", courseCode);
            mav.addObject("users", userList.get());
            mav.addObject("user_id", -1);
        } catch (Exception e) {
            mav.addObject("message", "Error Fetching Users");
        }
        return mav;
    }

    @PostMapping("/assign_ta/{courseCode}")
    public String handleAssignTA(@RequestParam("user_email") String userEmail,
                                 @PathVariable String courseCode,
                                 RedirectAttributes redirectAttributes) {
        logger.info(String.format("assigning %s as TA for course: %s", userEmail, courseCode));
        String roleName = "TA";
        try {
            Optional<User> user = userService.getUserFromEmail(userEmail);
            long courseId = courseService.getCourseWithCode(courseCode).get().getCourseId();
            Optional<Role> taRole = roleService.getRoleFromRoleName(roleName);
            userService.updateUserRole(user.get(), roleName);
            Enrollment enrollment = new Enrollment(user.get().getUserId(),
                    taRole.get().getRoleId(), courseId);
            enrollmentService.insertEnrollment(enrollment);
            redirectAttributes.addFlashAttribute("message",
                    String.format("TA has been assigned for course %s successfully.", courseCode));
            redirectAttributes.addFlashAttribute("isSuccess", true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Error while accessing database");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        return String.format("redirect:/assign_ta/%s", courseCode);
    }
}
