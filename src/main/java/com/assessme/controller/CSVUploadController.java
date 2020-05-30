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
import java.util.List;

@Controller
public class CSVUploadController {
    private Logger logger = LoggerFactory.getLogger(CSVUploadController.class);
    private StorageService service = CSVStorageService.getInstance();

    @GetMapping("/csvupload")
    public String csvFileUploadForm(Model model){
        return "csvupload";
    }

    @PostMapping("/csvupload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                                        RedirectAttributes redirectAttributes) {
        try {
            String newFileName = service.store(file);
            logger.info(String.format("New FileName: %s",newFileName));
            CSVReader reader = CSVImport.importFromPath(service.load(newFileName));
            logger.info("CSVParsed Successfully");
            List<String[]> allStudentsList = reader.readAll();
            for (String[] user : allStudentsList){
                logger.info(String.format("UserEmail: %s", user[3]));
            }

//            redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");
//            redirectAttributes.addFlashAttribute("isSuccess", true);

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message",
				"empty file was selected!");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        } catch(CsvException e){
            redirectAttributes.addFlashAttribute("message",
				"Error Parsing CSV File");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        return "redirect:/csvupload";
	}
}
