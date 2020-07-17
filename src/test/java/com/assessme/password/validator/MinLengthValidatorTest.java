package com.assessme.password.validator;

import com.assessme.auth.password.validator.MinLengthValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-18
 */
@SpringBootTest
public class MinLengthValidatorTest {

    private final Integer minLength = 2;

    private MinLengthValidatorImpl minLengthValidator;

    @BeforeEach
    public void init() {
        minLengthValidator = new MinLengthValidatorImpl(minLength);
    }

    @Test
    public void isValidTest() {

        Throwable exception = Assertions
            .assertThrows(IllegalArgumentException.class, () -> minLengthValidator.isValid(""));
        Assertions.assertEquals("Password cannot be null or blank", exception.getMessage());

        Assertions.assertTrue(minLengthValidator.isValid("Pa"),
            "Password should contain 2 or more characters");
        Assertions.assertFalse(minLengthValidator.isValid("F"),
            "Password should contain 2 or more characters");

    }
}
