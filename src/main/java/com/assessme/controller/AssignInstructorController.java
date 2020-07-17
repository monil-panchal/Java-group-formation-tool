package com.assessme.controller;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 12:28 AM
 */

import com.assessme.model.Enrollment;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.service.CourseService;
import com.assessme.service.CourseServiceImpl;
import com.assessme.service.EnrollmentService;
import com.assessme.service.EnrollmentServiceImpl;
import com.assessme.service.MailSenderService;
import com.assessme.service.MailSenderServiceImpl;
import com.assessme.service.RoleService;
import com.assessme.service.RoleServiceImpl;
import com.assessme.service.UserService;
import com.assessme.service.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AssignInstructorController {

    private final Logger logger = LoggerFactory.getLogger(AssignInstructorController.class);
    UserService userService;
    CourseService courseService;
    EnrollmentService enrollmentService;
    MailSenderService mailSenderService;
    RoleService roleService;

    public AssignInstructorController() {
        this.userService = UserServiceImpl.getInstance();
        this.courseService = CourseServiceImpl.getInstance();
        this.enrollmentService = EnrollmentServiceImpl.getInstance();
        this.mailSenderService = MailSenderServiceImpl.getInstance();
        this.roleService = RoleServiceImpl.getInstance();
    }


    @GetMapping("/assign_instructor/{courseCode}")
    public ModelAndView getPage(
        @PathVariable String courseCode) {
        logger.info("Serving for course: " + courseCode);
        ModelAndView mav = new ModelAndView("assign_instructor");
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

    @PostMapping("/assign_instructor/{courseCode}")
    public String handleAssignInstructor(@RequestParam("user_email") String userEmail,
        @PathVariable String courseCode,
        RedirectAttributes redirectAttributes) {
        logger.info(
            String.format("assigning %s as Instructor for course: %s", userEmail, courseCode));
        String roleName = "INSTRUCTOR";
        try {
            Optional<User> user = userService.getUserFromEmail(userEmail);
            long courseId = courseService.getCourseWithCode(courseCode).get().getCourseId();
            Optional<Role> taRole = roleService.getRoleFromRoleName(roleName);
            userService.updateUserRole(user.get(), roleName);
            Enrollment enrollment = new Enrollment(user.get().getUserId(),
                taRole.get().getRoleId(), courseId);
            enrollmentService.insertEnrollment(enrollment);
            redirectAttributes.addFlashAttribute("message",
                String.format("Instructor has been assigned for course %s successfully.",
                    courseCode));
            redirectAttributes.addFlashAttribute("isSuccess", true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                "Error while accessing database");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        return String.format("redirect:/assign_instructor/%s", courseCode);
    }
}
