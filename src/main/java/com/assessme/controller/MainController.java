package com.assessme.controller;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.db.dao.CourseDAOImpl;
import com.assessme.model.Course;
import com.assessme.model.User;
import com.assessme.service.CourseService;
import com.assessme.service.UserServiceImpl;
import com.assessme.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-05-28
 */

@Controller
@RequestMapping("/")
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);


    private UserServiceImpl userServiceImpl;
    private CourseService courseService;

    public MainController(UserServiceImpl userServiceImpl, CourseService courseService){
        this.userServiceImpl = userServiceImpl;
        this.courseService = courseService;
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/registration")
    public String registerUser(WebRequest request, Model model) {
        User userDto = new User();

        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") User user) throws Exception {
        Optional<User> registered = Optional.empty();
        try {
             registered = userServiceImpl.addUser(user, AppConstant.DEFAULT_USER_ROLE_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return "redirect:/home";
    }

    @GetMapping("/course_admin")
    public String courseAdmin(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "course_admin";
    }

    @PostMapping("/course_admin")
    public String addCourse(@ModelAttribute("course") Course course) throws Exception {
        logger.info("in add course");
        Optional<Course> courseToAdd = Optional.empty();
        try {
            courseToAdd = courseService.addCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return "course_admin";
    }

    @GetMapping("/course_info")
    public String courseInfo(Model model) {
        return "course_info";
    }

}
