package com.assessme.auth.password.validator;

import com.assessme.model.UserPasswordHistory;
import com.assessme.util.BcryptPasswordEncoderUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-06-17
 */
public class PasswordHistoryValidatorImpl implements PasswordValidator {

    private final Logger logger = LoggerFactory.getLogger(PasswordHistoryValidatorImpl.class);

    private final List<UserPasswordHistory> userPasswordHistoryList;

    public PasswordHistoryValidatorImpl(List<UserPasswordHistory> userPasswordHistoryList) {
        this.userPasswordHistoryList = userPasswordHistoryList;
    }

    @Override
    public Boolean isValid(String password) {

        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        System.out.println("userPasswordHistoryList: " + userPasswordHistoryList.toString());
        // No password update exists for the user
        if (userPasswordHistoryList == null || userPasswordHistoryList.isEmpty()) {
            return true;
        } else {
            for (UserPasswordHistory userPasswordHistory : userPasswordHistoryList) {
                // Calling the BcryptPasswordEncoderUtil to decode the previous passwords and match with the existing password
                Boolean isPasswordMatched = BcryptPasswordEncoderUtil
                    .matchPassword(password, userPasswordHistory.getPassword());

                // the new password is same with one of the previous password
                if (isPasswordMatched) {
                    logger.error(this.getClass().getName() + " validation failed");
                    return false;
                }
            }
            logger.info(this.getClass().getName() + " validation passed");
            return true;
        }
    }
}
