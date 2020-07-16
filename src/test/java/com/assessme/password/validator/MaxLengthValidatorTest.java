package com.assessme.password.validator;

import com.assessme.auth.password.validator.MaxLengthValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-18
 */
@SpringBootTest
public class MaxLengthValidatorTest {

    private final Integer maxLength = 8;

    private MaxLengthValidatorImpl maxLengthValidator;

    @BeforeEach
    public void init() {
        maxLengthValidator = new MaxLengthValidatorImpl(maxLength);
    }

    @Test
    public void isValidTest() {

        Throwable exception = Assertions
            .assertThrows(IllegalArgumentException.class, () -> maxLengthValidator.isValid(""));
        Assertions.assertEquals("Password cannot be null or blank", exception.getMessage());

        Assertions.assertTrue(maxLengthValidator.isValid("12345678"),
            "Password should not contain more than 8 characters");
        Assertions.assertFalse(maxLengthValidator.isValid("123456789"),
            "Password should not contain more than 8 characters");

    }

}
