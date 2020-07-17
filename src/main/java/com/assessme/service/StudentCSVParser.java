package com.assessme.service;

import com.assessme.model.User;
import java.util.List;
import java.util.Optional;

/**
 * @author Darshan Kathiriya
 * @created 15-June-2020 5:59 PM
 */
public interface StudentCSVParser {

    Optional<List<User>> parseStudents(List<String> failureResults);
}
