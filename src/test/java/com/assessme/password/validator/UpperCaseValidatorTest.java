package com.assessme.password.validator;

import com.assessme.auth.password.validator.UpperCaseValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-18
 */
@SpringBootTest
public class UpperCaseValidatorTest {

    private final Integer minLength = 2;

    private UpperCaseValidatorImpl upperCaseValidator;

    @BeforeEach
    public void init() {
        upperCaseValidator = new UpperCaseValidatorImpl(minLength);
    }

    @Test
    public void isValidTest() {

        Throwable exception = Assertions
            .assertThrows(IllegalArgumentException.class, () -> upperCaseValidator.isValid(""));
        Assertions.assertEquals("Password cannot be null or blank", exception.getMessage());

        Assertions.assertTrue(upperCaseValidator.isValid("PAss"),
            "Password should contain 2 or more uppercase characters");
        Assertions.assertFalse(upperCaseValidator.isValid("Fail"),
            "Password should contain 2 or more uppercase characters");

    }
}
