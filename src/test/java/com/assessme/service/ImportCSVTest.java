package com.assessme.service;

import org.junit.jupiter.api.Test;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.assessme.service.CSVImport.*;

public class ImportCSVTest {
    @Test
    public void importCSV() {
        try {
            String url = "https://gist.githubusercontent.com/dash2507/e8bbde96b5c1798088b8af81958e87f8/raw/8b2d04c4a3d930fa1021e15d4530886c7ef9cd24/students.csv";
            CSVReader reader = importFromUrl(url);
            Assertions.assertNotNull(reader);

            List<String[]> allStudentsList = reader.readAll();
            String[][] studentArray = new String[allStudentsList.size()][];
            allStudentsList.toArray(studentArray);
            String[][] expectedArray = {
                    {"B00123456", "Doe", "John", "john.doe@email.com"},
                    {"B00123789", "Doe", "Jane", "jane.doe@email.com"},
                    {"B00789123", "Christy", "Agatha", "cagatha@company.com"},
                    {"B00456789", "Brown", "Dan", "brownd@vinci.com"}
            };
            Assertions.assertArrayEquals(studentArray, expectedArray);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
