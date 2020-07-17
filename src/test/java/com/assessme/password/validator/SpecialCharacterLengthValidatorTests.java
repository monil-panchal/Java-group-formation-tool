package com.assessme.password.validator;

import com.assessme.auth.password.validator.SpecialCharacterLengthValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-18
 */
@SpringBootTest
public class SpecialCharacterLengthValidatorTests {

    private final Integer minLength = 2;

    private SpecialCharacterLengthValidatorImpl specialCharacterLengthValidator;

    @BeforeEach
    public void init() {

        specialCharacterLengthValidator = new SpecialCharacterLengthValidatorImpl(minLength);
    }

    @Test
    public void isValidTest() {

        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> specialCharacterLengthValidator.isValid(""));
        Assertions.assertEquals("Password cannot be null or blank", exception.getMessage());

        Assertions.assertTrue(specialCharacterLengthValidator.isValid("@password#"),
            "Password should contain 2 or more special characters");
        Assertions.assertFalse(specialCharacterLengthValidator.isValid("password"),
            "Password should contain 2 or more special characters");
        Assertions.assertFalse(specialCharacterLengthValidator.isValid("@password"),
            "Password should contain 2 or more special characters");

    }
}
