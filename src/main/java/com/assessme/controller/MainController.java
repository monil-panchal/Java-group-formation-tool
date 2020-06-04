package com.assessme.controller;

import com.assessme.model.PasswordDTO;
import com.assessme.model.User;
import com.assessme.model.UserToken;
import com.assessme.service.MailSenderService;
import com.assessme.service.UserServiceImpl;
import com.assessme.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: monil
 * Created on: 2020-05-28
 */

@Controller
@RequestMapping("/")
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private UserServiceImpl userServiceImpl;
    private MailSenderService mailSenderService;

    public MainController(UserServiceImpl userServiceImpl, MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
        this.userServiceImpl = userServiceImpl;
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
    public String registerUserAccount(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        logger.info(String.format("Saving Details for user %s", user));
        Optional<User> registered = Optional.empty();
        try {
            registered = userServiceImpl.addUser(user, AppConstant.DEFAULT_USER_ROLE_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Registration Failed");
        }
        return "redirect:/registration";
    }

    @GetMapping("/course_admin")
    public String courseAdmin(Model model) {
        return "course_admin";
    }

    @GetMapping("/course_info")
    public String courseInfo(Model model) {
        return "course_info";
    }

    @GetMapping("/forgetPassword")
    public String forgetPassword(@ModelAttribute("user") User user) {
        return "forgetPassword";
    }

    @PostMapping("/forgetPassword")
    public ModelAndView forgotUserPassword(ModelAndView modelAndView, @ModelAttribute("user") User user, HttpServletRequest request) throws Exception {

        try {

            Optional<UserToken> token = userServiceImpl.addUserToken(user.getEmail());
            String recipient = user.getEmail();
            String subject = AppConstant.PASSWORD_RESET_EMAIL_SUBJECT;

            URL requestURL = new URL(request.getRequestURL().toString());
            String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
            String serverUrl = requestURL.getProtocol() + "://" + requestURL.getHost() + port;

            String body = serverUrl + "/newPassword?" +
                    "email=" + user.getEmail() +
                    "&" +
                    "token=" + token.get().getToken();

            mailSenderService.sendSimpleMessage(recipient, subject, body);

            modelAndView.addObject("message", "An email is sent to your mailbox for password recovery.");
            // modelAndView.setViewName("successForgotPassword");

        } catch (Exception e) {
            modelAndView.addObject("message", e.getLocalizedMessage());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("/newPassword")
    public String changePassword(@ModelAttribute("user") User user , @RequestParam("email") String email, @RequestParam("token") String token ) throws Exception {
        Optional<UserToken> userToken = userServiceImpl.getUserToken(email);
        if (userToken.isEmpty()) {
            return "redirect:/login";
        } else {
            return "/newPassword";
        }
    }

    @PostMapping("/newPassword")
    public String changePassword(@ModelAttribute("user") User user) throws Exception {
        try {
            userServiceImpl.updateUserPassword(user, user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return "redirect:/home";
    }

}
