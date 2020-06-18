package com.assessme.password;

import com.assessme.auth.password.restriction.RegisterPasswordPolicyImpl;
import com.assessme.auth.password.validator.*;
import com.assessme.model.UserPasswordHistory;
import com.assessme.service.StoredPasswordPolicyService;
import com.assessme.util.AppConstant;
import com.assessme.util.BcryptPasswordEncoderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author: monil
 * Created on: 2020-06-17
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

        String userExistingPassword = "PassWord$";
        String encodedPassword1 = BcryptPasswordEncoderUtil.getbCryptPasswordFromPlainText(userExistingPassword);

        policyMap = new HashMap<>();
        policyMap.put(AppConstant.MIN_UPPER_CASE_CHARACTERS, "2");
        policyMap.put(AppConstant.MIN_SPECIAL_CHARACTERS, "1");
        policyMap.put(AppConstant.BLOCK_SPECIAL_CHARACTERS, "@");
        policyMap.put(AppConstant.PASSWORD_HISTORY_CONSTRAINT, "1");

        registerPasswordPolicies = new ArrayList<>();
        registerPasswordPolicies.add(new MinLengthValidatorImpl(2));
        registerPasswordPolicies.add(new SpecialCharacterLengthValidatorImpl(1));
        registerPasswordPolicies.add(new DisallowSpecialCharacterValidatorImpl("@"));

        List<UserPasswordHistory> userPasswordHistoryList = new ArrayList<>();
        userPasswordHistoryList.add(new UserPasswordHistory(1L, encodedPassword1, new Timestamp(Calendar.getInstance().getTime().getTime())));
        registerPasswordPolicies.add(new PasswordHistoryValidatorImpl(userPasswordHistoryList));

        registerPasswordPolicy.setPolicyMap(policyMap);
        registerPasswordPolicy.setRegisterPasswordPolicies(registerPasswordPolicies);

        registerPasswordPolicy.addPasswordRestrictions();

    }

    @Test
    public void isSatisfiedTest() throws Exception {

        Mockito.when(storedPasswordPolicyService.getPasswordPolicies()).thenReturn(policyMap);
        Assertions.assertTrue(registerPasswordPolicy.isSatisfied("PassWord$"));

        Throwable exception = Assertions.assertThrows(Exception.class, () -> registerPasswordPolicy.isSatisfied("Password"));
        Assertions.assertEquals("Password should match the policy: "+ policyMap , exception.getMessage());

        Throwable exception1 = Assertions.assertThrows(Exception.class, () -> registerPasswordPolicy.isSatisfied("PassWord@"));
        Assertions.assertEquals("Password should match the policy: "+ policyMap , exception1.getMessage());


    }
}
