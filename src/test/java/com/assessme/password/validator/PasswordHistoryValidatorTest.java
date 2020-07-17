package com.assessme.password.validator;

import com.assessme.auth.password.validator.PasswordHistoryValidatorImpl;
import com.assessme.model.UserPasswordHistory;
import com.assessme.util.BcryptPasswordEncoderUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-18
 */
@SpringBootTest
public class PasswordHistoryValidatorTest {

    private static final String userExistingPassword1 = "PassWord$";
    private static final String encodedPassword1 = BcryptPasswordEncoderUtil
        .getbCryptPasswordFromPlainText(userExistingPassword1);
    private static final String userExistingPassword2 = "@password";
    private static final String encodedPassword2 = BcryptPasswordEncoderUtil
        .getbCryptPasswordFromPlainText(userExistingPassword2);
    private List<UserPasswordHistory> userPasswordHistoryList;
    private PasswordHistoryValidatorImpl passwordHistoryValidator;

    @BeforeEach
    public void init() {

        userPasswordHistoryList = new ArrayList<>();
        userPasswordHistoryList.add(new UserPasswordHistory(1L, encodedPassword1,
            new Timestamp(Calendar.getInstance().getTime().getTime())));
        userPasswordHistoryList.add(new UserPasswordHistory(1L, encodedPassword2,
            new Timestamp(Calendar.getInstance().getTime().getTime())));
        passwordHistoryValidator = new PasswordHistoryValidatorImpl(userPasswordHistoryList);
    }

    @Test
    public void isValidTest() {

        Throwable exception = Assertions
            .assertThrows(IllegalArgumentException.class,
                () -> passwordHistoryValidator.isValid(""));
        Assertions.assertEquals("Password cannot be null or blank", exception.getMessage());

        Assertions.assertTrue(passwordHistoryValidator.isValid("Password"),
            "New Password should not match the existing password");
        Assertions.assertFalse(passwordHistoryValidator.isValid(userExistingPassword1),
            "New Password should not match the existing password");
        Assertions.assertFalse(passwordHistoryValidator.isValid(userExistingPassword2),
            "New Password should not match the existing password");
        Assertions.assertTrue(passwordHistoryValidator
                .isValid(userExistingPassword1 + "randomString" + userExistingPassword2),
            "New Password should not match the existing password");

    }
}


