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
import com.assessme.model.User;
import com.assessme.service.*;
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
    private UserServiceImpl userService;
    private CSVStorageService service;

    public CSVUploadController(CSVStorageService service, UserServiceImpl userServiceImpl) {
        this.service = service;
        this.userService = userServiceImpl;
    }


    @Autowired
    private EnrollmentDAO enrollmentDAO = EnrollmentDAOImpl.getInstance();

    private MailSenderService mailSenderService = MailSenderService.getInstance();

    @Autowired
    private RoleService roleService;

    @GetMapping("/csvupload/{courseCode}")
    public ModelAndView csvFileUploadForm(
            @PathVariable long courseCode) {
        ModelAndView mav = new ModelAndView("csvupload");
        logger.info("Serving for course: " + courseCode);
        mav.addObject("course_code", courseCode);
        return mav;
    }

    @PostMapping("/csvupload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("courseId") long courseId,
                                   RedirectAttributes redirectAttributes) {
        try {
            String newFileName = service.store(file);
            logger.info(String.format("New FileName: %s", newFileName));
            CSVReader reader = CSVImport.importFromPath(service.load(newFileName));
            logger.info("CSVParsed Successfully");
            List<String[]> allStudentsList = reader.readAll();
            Optional<Role> studentRole = roleService.getRoleFromRoleName("STUDENT");

            for (String[] csvRow : allStudentsList) {
                logger.info(String.format("UserEmail: %s", csvRow[3]));
                long userId;
                try {
                    Optional<User> userWithEmail = userService.getUserFromEmail(csvRow[3]);
                    logger.info("User: " + userWithEmail.get());
                    userId = userWithEmail.get().getUserId();
                } catch (Exception e) {
                    User newUser = new User();
                    newUser.setBannerId(csvRow[0]);
                    newUser.setLastName(csvRow[1]);
                    newUser.setFirstName(csvRow[2]);
                    newUser.setEmail(csvRow[3]);
                    Optional<User> insertedUser = userService.addUser(newUser, AppConstant.DEFAULT_USER_ROLE_CREATE);
                    userId = insertedUser.get().getUserId();
                    mailSenderService.sendSimpleMessage(insertedUser.get().getEmail(),
                            "Your Account Has Been Created",
                            "Your password is YourBannerId_YourLastName");
                }
                Enrollment enrollment = new Enrollment(userId, studentRole.get().getRoleId(), courseId);
                enrollmentDAO.insertEnrollment(enrollment);
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
            logger.error(e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Error while accessing database");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        return "redirect:/csvupload";
    }
}
