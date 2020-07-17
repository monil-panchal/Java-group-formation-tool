package com.assessme.controller;

import com.assessme.model.Course;
import com.assessme.model.User;
import com.assessme.model.UserToken;
import com.assessme.service.CourseService;
import com.assessme.service.CourseServiceImpl;
import com.assessme.service.MailSenderService;
import com.assessme.service.MailSenderServiceImpl;
import com.assessme.service.UserService;
import com.assessme.service.UserServiceImpl;
import com.assessme.util.AppConstant;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author: monil Created on: 2020-05-28
 */

@Controller
@RequestMapping("/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);


    private final UserService userService;
    private final CourseService courseService;
    private final MailSenderService mailSenderService;

    public MainController() {
        this.mailSenderService = MailSenderServiceImpl.getInstance();
        this.userService = UserServiceImpl.getInstance();
        this.courseService = CourseServiceImpl.getInstance();
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails currentUser)
            throws Exception {
        Optional<User> user = userService.getUserFromEmail(currentUser.getUsername());
        model.addAttribute("currentUser", user.get());
        return "home";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
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
        logger.info("Serving registration page.");
        User userDto = new User();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") User user,
                                      RedirectAttributes redirectAttributes) throws Exception {
        logger.info(String.format("Saving Details for user %s", user));
        Optional<User> registered = Optional.empty();
        try {
            registered = userService.addUser(user, AppConstant.DEFAULT_USER_ROLE_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

        return "redirect:/login";
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

    @GetMapping("/enrolled_courses")
    public String enrolledCourses(Model model) {
        return "enrolledCourses";
    }

    @GetMapping("/forget_password")
    public String forgetPassword(@ModelAttribute("user") User user) {
        return "forget_password";
    }

    @PostMapping("/forget_password")
    public ModelAndView forgotUserPassword(ModelAndView modelAndView,
                                           @ModelAttribute("user") User user, HttpServletRequest request) throws Exception {

        try {

            Optional<UserToken> token = userService.addUserToken(user.getEmail());
            String recipient = user.getEmail();
            String subject = AppConstant.PASSWORD_RESET_EMAIL_SUBJECT;

            URL requestURL = new URL(request.getRequestURL().toString());
            String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
            String serverUrl = requestURL.getProtocol() + "://" + requestURL.getHost() + port;

            String body = serverUrl + "/new_password?" +
                    "email=" + user.getEmail() +
                    "&" +
                    "token=" + token.get().getToken();

            mailSenderService.sendSimpleMessage(recipient, subject, body);

            // modelAndView.addObject("message", "An email is sent to your mailbox for password recovery.");
            // modelAndView.setViewName("successForgotPassword");

        } catch (Exception e) {
            //   modelAndView.addObject("message", e.getLocalizedMessage());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("/new_password")
    public String changePassword(@ModelAttribute("user") User user,
                                 @RequestParam("email") String email, @RequestParam("token") String token) throws Exception {
        Optional<UserToken> userToken = userService.getUserToken(email);
        if (userToken.isEmpty()) {
            return "redirect:/login";
        } else {
            return "new_password";
        }
    }

    @PostMapping("/new_password")
    public String changePassword(@ModelAttribute("user") User user) throws Exception {
        try {
            userService.updateUserPassword(user, user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return "redirect:/login";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception exception) {
        logger.error("Request: " + request.getRequestURL() + " raised " + exception);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", exception.getLocalizedMessage());
        modelAndView.addObject("path", request.getRequestURL());
        modelAndView.addObject("timestamp", new Date().toInstant());
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
