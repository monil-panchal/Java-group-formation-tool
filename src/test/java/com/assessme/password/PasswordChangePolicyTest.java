package com.assessme.password;

import com.assessme.auth.password.restriction.PasswordChangePolicyImpl;
import com.assessme.auth.password.validator.DisallowSpecialCharacterValidatorImpl;
import com.assessme.auth.password.validator.MinLengthValidatorImpl;
import com.assessme.auth.password.validator.PasswordHistoryValidatorImpl;
import com.assessme.auth.password.validator.PasswordValidator;
import com.assessme.auth.password.validator.SpecialCharacterLengthValidatorImpl;
import com.assessme.model.UserPasswordHistory;
import com.assessme.service.StoredPasswordPolicyService;
import com.assessme.util.AppConstant;
import com.assessme.util.BcryptPasswordEncoderUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: monil Created on: 2020-06-17
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PasswordChangePolicyTest {

    @Mock
    private StoredPasswordPolicyService storedPasswordPolicyService;

    @Mock
    private PasswordChangePolicyImpl passwordChangePolicy;

    private Map<String, Object> policyMap;
    private List<PasswordValidator> passwordValidators;

    private String userExistingPassword;

    @BeforeEach
    public void init() throws Exception {

        userExistingPassword = "PassWord$";

        String encodedPassword1 = BcryptPasswordEncoderUtil
            .getbCryptPasswordFromPlainText(userExistingPassword);

        policyMap = new HashMap<>();

        policyMap.put(AppConstant.MIN_UPPER_CASE_CHARACTERS, "2");
        policyMap.put(AppConstant.MIN_SPECIAL_CHARACTERS, "1");
        policyMap.put(AppConstant.BLOCK_SPECIAL_CHARACTERS, "@");
        policyMap.put(AppConstant.PASSWORD_HISTORY_CONSTRAINT, "1");

        passwordValidators = new ArrayList<>();
        passwordValidators.add(new MinLengthValidatorImpl(2));
        passwordValidators.add(new SpecialCharacterLengthValidatorImpl(1));
        passwordValidators.add(new DisallowSpecialCharacterValidatorImpl("@"));

        List<UserPasswordHistory> userPasswordHistoryList = new ArrayList<>();
        userPasswordHistoryList.add(new UserPasswordHistory(1L, encodedPassword1,
            new Timestamp(Calendar.getInstance().getTime().getTime())));
        passwordValidators.add(new PasswordHistoryValidatorImpl(userPasswordHistoryList));

    }

    @Test
    public void isSatisfiedTest() throws Exception {

        passwordChangePolicy.addPasswordRestrictions(1L);
        passwordChangePolicy.setPolicyMap(policyMap);
        passwordChangePolicy.setRegisterPasswordPolicies(passwordValidators);
        Mockito.when(passwordChangePolicy.isSatisfied("PassWord#")).thenReturn(true);
        Mockito.when(storedPasswordPolicyService.getPasswordPolicies()).thenReturn(policyMap);

        Assertions.assertTrue(passwordChangePolicy.isSatisfied("PassWord#"));

    }
}
