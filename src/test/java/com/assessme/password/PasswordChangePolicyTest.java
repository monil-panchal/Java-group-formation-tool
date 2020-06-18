package com.assessme.password;

import com.assessme.auth.password.restriction.PasswordChangePolicyImpl;
import com.assessme.auth.password.restriction.RegisterPasswordPolicyImpl;
import com.assessme.auth.password.validator.*;
import com.assessme.model.UserPasswordHistory;
import com.assessme.service.StoredPasswordPolicyService;
import com.assessme.service.UserPasswordHistoryServiceImpl;
import com.assessme.util.AppConstant;
import com.assessme.util.BcryptPasswordEncoderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author: monil
 * Created on: 2020-06-17
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PasswordChangePolicyTest {

    @Mock
    private StoredPasswordPolicyService storedPasswordPolicyService;

    @InjectMocks
    private PasswordChangePolicyImpl passwordChangePolicy;

    private Map<String, Object> policyMap;
    private List<PasswordValidator> passwordValidators;

    @BeforeEach
    public void init() throws Exception {

        policyMap = new HashMap<>();

        policyMap.put(AppConstant.MIN_UPPER_CASE_CHARACTERS, "2");
        policyMap.put(AppConstant.MIN_SPECIAL_CHARACTERS, "1");
        policyMap.put(AppConstant.BLOCK_SPECIAL_CHARACTERS, "@");

        passwordValidators = new ArrayList<>();
        passwordValidators.add(new MinLengthValidatorImpl(2));
        passwordValidators.add(new SpecialCharacterLengthValidatorImpl(1));
        passwordValidators.add(new DisallowSpecialCharacterValidatorImpl("@"));

        passwordChangePolicy.setPolicyMap(policyMap);
        passwordChangePolicy.setRegisterPasswordPolicies(passwordValidators);


        passwordChangePolicy.addPasswordRestrictions(1L);

    }

    @Test
    public void isSatisfiedTest() throws Exception {

        Mockito.when(storedPasswordPolicyService.getPasswordPolicies()).thenReturn(policyMap);


        Assertions.assertTrue(passwordChangePolicy.isSatisfied("PassWord#"));

        Throwable exception = Assertions.assertThrows(Exception.class, () -> passwordChangePolicy.isSatisfied("Password"));
        Assertions.assertEquals("Password should match the policy: " + policyMap, exception.getMessage());

        Throwable exception1 = Assertions.assertThrows(Exception.class, () -> passwordChangePolicy.isSatisfied("PassWord@"));
        Assertions.assertEquals("Password should match the policy: " + policyMap, exception1.getMessage());


    }
}
