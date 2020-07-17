package com.assessme.controller;

import com.assessme.auth.CurrentUserService;
import com.assessme.model.Course;
import com.assessme.model.Enrollment;
import com.assessme.model.ResponseDTO;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.service.CourseService;
import com.assessme.service.CourseServiceImpl;
import com.assessme.service.EnrollmentService;
import com.assessme.service.EnrollmentServiceImpl;
import com.assessme.service.RoleService;
import com.assessme.service.RoleServiceImpl;
import com.assessme.service.StudentCSVImport;
import com.assessme.service.StudentCSVParser;
import com.assessme.service.StudentCSVParserImpl;
import com.assessme.service.UserService;
import com.assessme.service.UserServiceImpl;
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

    private final Logger logger = LoggerFactory.getLogger(InstructorCourseController.class);
    UserService userService;
    CourseService courseService;
    EnrollmentService enrollmentService;
    RoleService roleService;
    CurrentUserService currentUserService;
    StudentCSVImport studentCSVImport;

    public InstructorCourseController() {
        this.userService = UserServiceImpl.getInstance();
        this.courseService = CourseServiceImpl.getInstance();
        this.enrollmentService = EnrollmentServiceImpl.getInstance();
        this.roleService = RoleServiceImpl.getInstance();
        this.currentUserService = CurrentUserService.getInstance();
        this.studentCSVImport = StudentCSVImport.getInstance();
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
            logger.info("is user Instructor: " + currentUserService.isInstructor());
            mav.addObject("isInstructor", currentUserService.isInstructor());
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
            responseDTO = new ResponseDTO(true, null, null, null);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            responseDTO = new ResponseDTO(false, null, null, null);
            httpStatus = HttpStatus.OK;
        }
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
        StudentCSVParser parser = new StudentCSVParserImpl(file);
        try {
            studentCSVImport.importStudents(parser, courseCode);
            redirectAttributes.addFlashAttribute("message",
                "Users has been successfully created!");
            redirectAttributes.addFlashAttribute("isSuccess", true);
            redirectAttributes.addFlashAttribute("successResults",
                studentCSVImport.getSuccessResults());
            redirectAttributes.addFlashAttribute("failureResults",
                studentCSVImport.getFailureResults());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                "Error while accessing database");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        return String.format("redirect:/csvupload/%s", courseCode);
    }
}
