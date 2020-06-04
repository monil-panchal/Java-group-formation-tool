package com.assessme.controller;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 12:28 AM
 */

import com.assessme.model.User;
import com.assessme.service.CSVImport;
import com.assessme.service.CSVStorageService;
import com.assessme.service.UserServiceImpl;
import com.assessme.db.dao.EnrollmentDAO;
import com.assessme.db.dao.EnrollmentDAOImpl;
import com.assessme.model.Enrollment;
import com.assessme.model.Role;
import com.assessme.service.*;
import com.assessme.service.CourseService;
import com.assessme.util.AppConstant;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class CSVUploadController {

    private Logger logger = LoggerFactory.getLogger(CSVUploadController.class);

    StorageService storageService;
    UserService userService;
    CourseService courseService;
    EnrollmentService enrollmentService;
    MailSenderService mailSenderService;
    RoleService roleService;

    public CSVUploadController(StorageService storageService, UserService userService,
                               CourseService courseService, EnrollmentService enrollmentService,
                               MailSenderService mailSenderService, RoleService roleService) {
        this.storageService = storageService;
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.mailSenderService = mailSenderService;
        this.roleService = roleService;
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
                        (long)courseService.getCourseWithCode(courseCode).get().getCourseId()
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
