package com.assessme.service;

import com.assessme.model.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class StudentCSVParserImpl implements StudentCSVParser {

    private final Logger logger = LoggerFactory.getLogger(StudentCSVParserImpl.class);
    MultipartFile file;

    public StudentCSVParserImpl(MultipartFile file) {
        this.file = file;
    }

    public Optional<List<User>> parseStudents(List<String> failureResults) {
        try {
            Reader inputStreamReader = new InputStreamReader(file.getInputStream());
            CSVReader reader = new CSVReaderBuilder(inputStreamReader).build();
            List<String[]> allRows = reader.readAll();
            logger.info("CSVParsed Successfully");
            List<User> studentList = new ArrayList<>();
            User newUser;
            for (String[] csvRow : allRows) {
                newUser = new User();
                newUser.setBannerId(csvRow[0]);
                newUser.setLastName(csvRow[1]);
                newUser.setFirstName(csvRow[2]);
                newUser.setEmail(csvRow[3]);
                studentList.add(newUser);
            }
            return Optional.of(studentList);
        } catch (IOException e) {
            failureResults.add("Failure reading uploaded file: " + e.getMessage());
        } catch (Exception e) {
            failureResults.add("Failure parsing CSV file: " + e.getMessage());
        }
        return Optional.empty();
    }
}
