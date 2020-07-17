package com.assessme.auth.password.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-06-17
 */
public class UpperCaseValidatorImpl implements PasswordValidator {

    private final Logger logger = LoggerFactory.getLogger(UpperCaseValidatorImpl.class);

    private Integer minLength;

    public UpperCaseValidatorImpl(Integer minLength) {
        this.minLength = minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    @Override
    public Boolean isValid(String password) {

        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        Integer upperCaseCounter = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                upperCaseCounter++;

                if (upperCaseCounter >= this.minLength) {
                    logger.info(this.getClass().getName() + " validation passed");
                    return true;
                } else {
                    continue;
                }
            }
        }
        logger.error(this.getClass().getName() + " validation failed");
        return false;
    }
}
