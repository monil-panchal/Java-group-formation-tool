package com.assessme.service;

import com.assessme.db.dao.PasswordPoliciesDAOImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: monil
 * Created on: 2020-06-18
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StoredPasswordPolicyServiceImplTests {

    private Logger logger = LoggerFactory.getLogger(StoredPasswordPolicyServiceImplTests.class);

    @Mock
    private PasswordPoliciesDAOImpl passwordPoliciesDAO;

    @InjectMocks
    private StoredPasswordPolicyServiceImpl storedPasswordPolicyService;

    @Test
    public void getUserPasswordHistory() throws Exception {

        logger.info("Running unit test for getting password policies");

        String minUppercaseCharacters = "minUppercaseCharacters";
        Integer value = 2;

        Map<String, Object> policyMap = new HashMap<>();
        policyMap.put(minUppercaseCharacters, value);

        Mockito.when(passwordPoliciesDAO.getAllPasswordPolicies()).thenReturn(policyMap);
        Assert.notEmpty(storedPasswordPolicyService.getPasswordPolicies(), "password list from DB not null");
    }
}