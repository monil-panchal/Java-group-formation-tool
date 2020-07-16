package com.assessme.service;

import com.assessme.db.dao.PasswordPoliciesDAOImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: monil Created on: 2020-06-18
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StoredPasswordPolicyServiceImplTests {

    private final Logger logger = LoggerFactory
        .getLogger(StoredPasswordPolicyServiceImplTests.class);

    @Mock
    private PasswordPoliciesDAOImpl passwordPoliciesDAO;

    @Mock
    private StoredPasswordPolicyServiceImpl storedPasswordPolicyService;

    @Test
    public void getUserPasswordHistory() throws Exception {

        logger.info("Running unit test for getting password policies");

        String minUppercaseCharacters = "minUppercaseCharacters";
        Integer value = 2;

        Map<String, Object> policyMap = new HashMap<>();
        policyMap.put(minUppercaseCharacters, value);

        Mockito.when(storedPasswordPolicyService.getPasswordPolicies()).thenReturn(policyMap);
        Assert.notEmpty(storedPasswordPolicyService.getPasswordPolicies(),
            "password list from DB not null");
    }
}
