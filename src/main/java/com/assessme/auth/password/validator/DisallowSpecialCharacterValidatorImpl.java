package com.assessme.auth.password.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-06-17
 */
public class DisallowSpecialCharacterValidatorImpl implements PasswordValidator {

    private final Logger logger = LoggerFactory
        .getLogger(DisallowSpecialCharacterValidatorImpl.class);

    private final String disallowCharacters;

    public DisallowSpecialCharacterValidatorImpl(String disallowCharacters) {
        this.disallowCharacters = disallowCharacters;
    }

    @Override
    public Boolean isValid(String password) {

        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        String[] passwordSplit = password.split("");

        for (int i = 0; i < passwordSplit.length; i++) {
            // Password contains one disallowed special character
            if (disallowCharacters.contains(passwordSplit[i])) {
                logger.error(this.getClass().getName() + " validation failed");
                return false;
            }
        }
        logger.info(this.getClass().getName() + " validation passed");
        return true;
    }
}
