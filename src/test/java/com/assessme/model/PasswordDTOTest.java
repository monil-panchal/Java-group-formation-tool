package com.assessme.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: hardik
 * Created on: 2020-06-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SuppressWarnings("deprecation")
public class PasswordDTOTest {
    private Logger logger = LoggerFactory.getLogger(PasswordDTO.class);

    public void ConstructorTests() {
        logger.info("Running unit test for PasswordDTO constructor");

        PasswordDTO passwordDTO = new PasswordDTO();
        Assert.isNull(passwordDTO.getPassword());

        String password = "password";
        PasswordDTO passwordDTO1 = new PasswordDTO(password);
        Assert.isTrue(passwordDTO1.getPassword().equals(password));
    }

    public void getPasswordTest() {
        logger.info("Running unit test for fetching password from PasswordDTO");

        String password = "password";
        PasswordDTO passwordDTO = new PasswordDTO(password);
        Assert.isTrue(passwordDTO.getPassword().equals(password));
    }

    public void setPasswordTest() {
        logger.info("Running unit test for setting password from PasswordDTO constructor");

        PasswordDTO passwordDTO = new PasswordDTO();
        Assert.isNull(passwordDTO.getPassword());

        String password = "password";
        passwordDTO.setPassword(password);
        Assert.isTrue(passwordDTO.getPassword().equals(password));

    }
}
