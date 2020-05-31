package com.assessme.controller;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 12:28 AM
 */

import com.assessme.model.ResponseDTO;
import com.assessme.model.User;
import com.assessme.service.CSVImport;
import com.assessme.service.CSVStorageService;
import com.assessme.service.StorageService;
import com.assessme.service.UserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CSVUploadController {

    private Logger logger = LoggerFactory.getLogger(CSVUploadController.class);
    private StorageService service = CSVStorageService.getInstance();
    private UserService userService = UserService.getInstance();

    @GetMapping("/csvupload")
    public String csvFileUploadForm(Model model) {
        return "csvupload";
    }

    @PostMapping("/csvupload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            String newFileName = service.store(file);
            logger.info(String.format("New FileName: %s", newFileName));
            CSVReader reader = CSVImport.importFromPath(service.load(newFileName));
            logger.info("CSVParsed Successfully");
            List<String[]> allStudentsList = reader.readAll();
            List<String> successfullInsertion = new ArrayList<>();
            List<String> failedInsertion = new ArrayList<>();

            for (String[] userRow : allStudentsList) {
                logger.info(String.format("UserEmail: %s", userRow[3]));
                Optional<User> userWithEmail = userService.getUserFromEmail(userRow[3]);
                if (userWithEmail.isPresent()) {
                    // update uer role for that student.
                    long userId = userWithEmail.get().getUserId();
//                    userService.updateUserRole(userId, "UserRole");
                } else {
                    User user = new User(userRow[0], userRow[1], userRow[2], userRow[3], "password", true);
//                    userService.insertUser();
                    Optional<User> insertedUser = userService.getUserFromEmail(userRow[3]);
                    if (insertedUser.isPresent()) {
                        successfullInsertion.add(insertedUser.get().getBannerId());
                    }else{
                        failedInsertion.add(userRow[1]);
                    }
                }
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
            redirectAttributes.addFlashAttribute("message",
                    "Error while accessing database");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        return "redirect:/csvupload";
    }
}
