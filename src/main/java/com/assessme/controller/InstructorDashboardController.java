package com.assessme.controller;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.Course;
import com.assessme.model.Role;
import com.assessme.service.CourseService;
import com.assessme.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * @author Darshan Kathiriya
 * @created 04-June-2020 3:48 PM
 */
@Controller
public class InstructorDashboardController {
    private Logger logger = LoggerFactory.getLogger(InstructorDashboardController.class);

    CourseService courseService;
    RoleService roleService;

    public InstructorDashboardController(CourseService courseService, RoleService roleService) {
        this.courseService = courseService;
        this.roleService = roleService;
    }

    @GetMapping("/instructor_dashboard")
    public String getDashboardAfterProcessing(Model model){
        return "instructor_dashboard";
    }

    @PostMapping("/instructor_dashboard")
    public String getDashboard(@RequestParam long userId, Model model){
        logger.info(String.format("Getting Dashboard for Instructor %d", userId));
        try{
            int roleIdIns = roleService.getRoleFromRoleName("INSTRUCTOR").get().getRoleId();
            int roleIdTa = roleService.getRoleFromRoleName("TA").get().getRoleId();
            List<Course> courseList = courseService.getCoursesByUserAndRole(userId, roleIdTa).get();
            courseList.addAll(courseService.getCoursesByUserAndRole(userId, roleIdIns).get());
            model.addAttribute("courses", courseList);
            model.addAttribute("userId", userId);
        }catch(Exception e){
            logger.error("Error Getting Courses");
            model.addAttribute("message", "Error Fetching Courses");
        }
        return "instructor_dashboard";
    }

}
