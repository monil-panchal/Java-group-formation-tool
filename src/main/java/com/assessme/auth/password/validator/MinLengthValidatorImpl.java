package com.assessme.auth.password.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-06-17
 */
public class MinLengthValidatorImpl implements PasswordValidator {

    private final Logger logger = LoggerFactory.getLogger(MinLengthValidatorImpl.class);

    private final Integer minLength;

    public MinLengthValidatorImpl(Integer minLength) {
        this.minLength = minLength;
    }

    @Override
    public Boolean isValid(String password) {
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        if (password.length() >= this.minLength) {
            logger.info(this.getClass().getName() + " validation passed");
            return true;
        }
        logger.error(this.getClass().getName() + " validation failed");
        return false;
    }
}
