package com.assessme.password.validator;

import com.assessme.auth.password.validator.DisallowSpecialCharacterValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-18
 */
@SpringBootTest
public class DisallowSpecialCharacterValidatorTest {

    private final String disallowCharacters = "@$";

    private DisallowSpecialCharacterValidatorImpl disallowSpecialCharacterValidator;

    @BeforeEach
    public void init() {
        disallowSpecialCharacterValidator = new DisallowSpecialCharacterValidatorImpl(
            disallowCharacters);
    }

    @Test
    public void isValidTest() {

        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> disallowSpecialCharacterValidator.isValid(""));
        Assertions.assertEquals("Password cannot be null or blank", exception.getMessage());

        Assertions.assertTrue(disallowSpecialCharacterValidator.isValid("password"),
            String.format("Password should not contain the characters: %s", disallowCharacters));
        Assertions.assertTrue(disallowSpecialCharacterValidator.isValid("%^&password"),
            String.format("Password should not contain the characters: %s", disallowCharacters));
        Assertions.assertFalse(disallowSpecialCharacterValidator.isValid("@password"),
            String.format("Password should not contain the characters: %s", disallowCharacters));
        Assertions.assertFalse(disallowSpecialCharacterValidator.isValid("password$"),
            String.format("Password should not contain the characters: %s", disallowCharacters));
        Assertions.assertFalse(disallowSpecialCharacterValidator.isValid("@pass$word"),
            String.format("Password should not contain the characters: %s", disallowCharacters));

    }

}
