package com.assessme.password;

import com.assessme.auth.password.restriction.RegisterPasswordPolicyImpl;
import com.assessme.auth.password.validator.DisallowSpecialCharacterValidatorImpl;
import com.assessme.auth.password.validator.MinLengthValidatorImpl;
import com.assessme.auth.password.validator.PasswordValidator;
import com.assessme.auth.password.validator.SpecialCharacterLengthValidatorImpl;
import com.assessme.service.StoredPasswordPolicyService;
import com.assessme.util.AppConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-17
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RegisterPasswordPolicyTest {

    @Mock
    private StoredPasswordPolicyService storedPasswordPolicyService;

    @InjectMocks
    private RegisterPasswordPolicyImpl registerPasswordPolicy;

    private Map<String, Object> policyMap;
    private List<PasswordValidator> registerPasswordPolicies;


    @BeforeEach
    public void init() throws Exception {

        policyMap = new HashMap<>();
        policyMap.put(AppConstant.MIN_UPPER_CASE_CHARACTERS, "2");
        policyMap.put(AppConstant.MIN_SPECIAL_CHARACTERS, "1");
        policyMap.put(AppConstant.BLOCK_SPECIAL_CHARACTERS, "@");

        registerPasswordPolicies = new ArrayList<>();
        registerPasswordPolicies.add(new MinLengthValidatorImpl(2));
        registerPasswordPolicies.add(new SpecialCharacterLengthValidatorImpl(1));
        registerPasswordPolicies.add(new DisallowSpecialCharacterValidatorImpl("@"));

    }

    @Test
    public void isSatisfiedTest() throws Exception {

        registerPasswordPolicy.setPolicyMap(policyMap);
        registerPasswordPolicy.setRegisterPasswordPolicies(registerPasswordPolicies);

        Mockito.when(storedPasswordPolicyService.getPasswordPolicies()).thenReturn(policyMap);
        Assertions.assertTrue(registerPasswordPolicy.isSatisfied("PassWord$"));

        Throwable minUpperCaseException = Assertions
            .assertThrows(Exception.class, () -> registerPasswordPolicy.isSatisfied("Password"));
        Assertions.assertEquals("Password should match the policy: " + policyMap,
            minUpperCaseException.getMessage());

        Throwable disallowSpecialCharacterException = Assertions
            .assertThrows(Exception.class, () -> registerPasswordPolicy.isSatisfied("PassWord@"));
        Assertions.assertEquals("Password should match the policy: " + policyMap,
            disallowSpecialCharacterException.getMessage());


    }
}
