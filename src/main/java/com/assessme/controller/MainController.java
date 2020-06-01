package com.assessme.controller;

import com.assessme.model.User;
import com.assessme.service.UserServiceImpl;
import com.assessme.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-05-28
 */

@Controller
@RequestMapping("/")
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        return "home";
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
             registered = userService.addUser(user, AppConstant.DEFAULT_USER_ROLE_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return "redirect:/home";
    }

}
