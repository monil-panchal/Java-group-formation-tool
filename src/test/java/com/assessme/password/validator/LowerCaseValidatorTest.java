package com.assessme.password.validator;

import com.assessme.auth.password.validator.LowerCaseValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-18
 */
@SpringBootTest
public class LowerCaseValidatorTest {

    private final Integer minLength = 2;

    private LowerCaseValidatorImpl lowerCaseValidator;

    @BeforeEach
    public void init() {
        lowerCaseValidator = new LowerCaseValidatorImpl(minLength);
    }

    @Test
    public void isValidTest() {

        Throwable exception = Assertions
            .assertThrows(IllegalArgumentException.class, () -> lowerCaseValidator.isValid(""));
        Assertions.assertEquals("Password cannot be null or blank", exception.getMessage());

        Assertions.assertTrue(lowerCaseValidator.isValid("Pass"),
            "Password should contain 2 or more lowercase characters");
        Assertions.assertFalse(lowerCaseValidator.isValid("F"),
            "Password should contain 2 or more lowercase characters");

    }
}
