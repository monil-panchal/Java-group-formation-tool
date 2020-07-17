package com.assessme.auth.password.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-06-17
 */
public class SpecialCharacterLengthValidatorImpl implements PasswordValidator {

    private final Logger logger = LoggerFactory
        .getLogger(SpecialCharacterLengthValidatorImpl.class);

    private final Integer minLength;

    public SpecialCharacterLengthValidatorImpl(Integer minLength) {
        this.minLength = minLength;
    }

    @Override
    public Boolean isValid(String password) {
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        int specialCharacterCounter = 0;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isSpaceChar(ch)) {
                return false;
            }

            if (Character.isLetterOrDigit(ch)) {
                continue;
            } else {
                specialCharacterCounter++;
            }
        }

        if (specialCharacterCounter >= minLength) {
            logger.info(this.getClass().getName() + " validation passed");
            return true;
        }

        logger.error(this.getClass().getName() + " validation failed");
        return false;
    }
}
