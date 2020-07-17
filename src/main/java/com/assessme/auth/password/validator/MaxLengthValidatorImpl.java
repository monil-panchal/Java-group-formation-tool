package com.assessme.auth.password.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-06-17
 */
public class MaxLengthValidatorImpl implements PasswordValidator {

    private final Logger logger = LoggerFactory.getLogger(MaxLengthValidatorImpl.class);

    private final Integer maxLength;

    public MaxLengthValidatorImpl(Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public Boolean isValid(String password) {
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        if (password.length() <= this.maxLength) {
            logger.info(this.getClass().getName() + " validation passed");
            return true;
        }
        logger.error(this.getClass().getName() + " validation failed");
        return false;
    }
}
