package com.assessme.auth.password.restriction;

import com.assessme.auth.password.validator.DisallowSpecialCharacterValidatorImpl;
import com.assessme.auth.password.validator.LowerCaseValidatorImpl;
import com.assessme.auth.password.validator.MaxLengthValidatorImpl;
import com.assessme.auth.password.validator.MinLengthValidatorImpl;
import com.assessme.auth.password.validator.PasswordHistoryValidatorImpl;
import com.assessme.auth.password.validator.PasswordValidator;
import com.assessme.auth.password.validator.SpecialCharacterLengthValidatorImpl;
import com.assessme.auth.password.validator.UpperCaseValidatorImpl;
import com.assessme.model.UserPasswordHistory;
import com.assessme.service.StoredPasswordPolicyService;
import com.assessme.service.StoredPasswordPolicyServiceImpl;
import com.assessme.service.UserPasswordHistoryServiceImpl;
import com.assessme.util.AppConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: monil Created on: 2020-06-17
 */
@Service
public class PasswordChangePolicyImpl implements PasswordPolicy {

    private static PasswordChangePolicyImpl instance;
    private final Logger logger = LoggerFactory.getLogger(PasswordChangePolicyImpl.class);
    private final StoredPasswordPolicyService storedPasswordPolicyService;
    private final UserPasswordHistoryServiceImpl userPasswordHistoryService;
    private Map<String, Object> policyMap;
    private List<PasswordValidator> registerPasswordPolicies;
    private List<UserPasswordHistory> userPasswordHistoryList;

    public PasswordChangePolicyImpl() {
        this.storedPasswordPolicyService = StoredPasswordPolicyServiceImpl.getInstance();
        this.userPasswordHistoryService = UserPasswordHistoryServiceImpl.getInstance();
        this.init();
    }

    public static PasswordChangePolicyImpl getInstance() {
        if (instance == null) {
            instance = new PasswordChangePolicyImpl();
        }
        return instance;
    }

    public Map<String, Object> getPolicyMap() {
        return policyMap;
    }

    public void setPolicyMap(Map<String, Object> policyMap) {
        this.policyMap = policyMap;
    }

    public List<PasswordValidator> getRegisterPasswordPolicies() {
        return registerPasswordPolicies;
    }

    public void setRegisterPasswordPolicies(List<PasswordValidator> registerPasswordPolicies) {
        this.registerPasswordPolicies = registerPasswordPolicies;
    }

    public List<UserPasswordHistory> getUserPasswordHistoryList() {
        return userPasswordHistoryList;
    }

    public void setUserPasswordHistoryList(List<UserPasswordHistory> userPasswordHistoryList) {
        this.userPasswordHistoryList = userPasswordHistoryList;
    }

    private void init() {
        try {
            policyMap = storedPasswordPolicyService.getPasswordPolicies();
            logger.info(String.format("PolicyMap from service: %s", policyMap));
            if (policyMap == null) {
                logger.info("No password policy found. Skipping the policy builder");
            }
        } catch (Exception e) {

        }
    }

    public void addPasswordRestrictions(Long userId) throws Exception {
        logger.info(String.format("Calling addPasswordRestrictions"));
        policyMap = storedPasswordPolicyService.getPasswordPolicies();
        registerPasswordPolicies = new ArrayList<>();

        for (Map.Entry<String, Object> entry : policyMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (AppConstant.MIN_UPPER_CASE_CHARACTERS.equalsIgnoreCase(key) && value != null) {
                Integer minUpperCaseLength = Integer.valueOf((String) value);
                if (minUpperCaseLength > 0) {
                    PasswordValidator upperCaseValidator = new UpperCaseValidatorImpl(
                        minUpperCaseLength);
                    registerPasswordPolicies.add(upperCaseValidator);
                }
            } else if (AppConstant.MIN_LOWER_CASE_CHARACTERS.equalsIgnoreCase(key)
                && value != null) {
                Integer minLowerCaseLength = Integer.valueOf((String) value);
                if (minLowerCaseLength > 0) {
                    PasswordValidator lowPasswordValidator = new LowerCaseValidatorImpl(
                        minLowerCaseLength);
                    registerPasswordPolicies.add(lowPasswordValidator);
                }
            } else if (AppConstant.MIN_PASSWORD_LENGTH.equalsIgnoreCase(key) && value != null) {
                Integer minPasswordLength = Integer.valueOf((String) value);
                if (minPasswordLength > 0) {
                    PasswordValidator minLengthValidator = new MinLengthValidatorImpl(
                        minPasswordLength);
                    registerPasswordPolicies.add(minLengthValidator);
                }
            } else if (AppConstant.MAX_PASSWORD_LENGTH.equalsIgnoreCase(key) && value != null) {
                Integer maxPasswordLength = Integer.valueOf((String) value);
                if (maxPasswordLength > 0) {
                    PasswordValidator maxLengthValidator = new MaxLengthValidatorImpl(
                        maxPasswordLength);
                    registerPasswordPolicies.add(maxLengthValidator);
                }
            } else if (AppConstant.BLOCK_SPECIAL_CHARACTERS.equalsIgnoreCase(key)
                && value != null) {
                String blockSpecialCharacterRegex = String.valueOf(value);
                PasswordValidator specialCharacterValidator = new DisallowSpecialCharacterValidatorImpl(
                    blockSpecialCharacterRegex);
                registerPasswordPolicies.add(specialCharacterValidator);

            } else if (AppConstant.MIN_SPECIAL_CHARACTERS.equalsIgnoreCase(key) && value != null) {
                Integer minSpecialCharacters = Integer.valueOf((String) value);
                if (minSpecialCharacters > 0) {
                    PasswordValidator specialCharacterLengthValidator = new SpecialCharacterLengthValidatorImpl(
                        minSpecialCharacters);
                    registerPasswordPolicies.add(specialCharacterLengthValidator);
                }
            } else if (AppConstant.PASSWORD_HISTORY_CONSTRAINT.equalsIgnoreCase(key)
                && value != null) {
                Integer lastPasswordConstraint = Integer.valueOf((String) value);
                if (lastPasswordConstraint > 0) {
                    userPasswordHistoryList = this.userPasswordHistoryService
                        .getUserPasswordHistory(userId, lastPasswordConstraint);

                    if (userPasswordHistoryList == null || userPasswordHistoryList.isEmpty()) {
                        continue;
                    } else {
                        PasswordValidator passwordHistoryValidator = new PasswordHistoryValidatorImpl(
                            userPasswordHistoryList);
                        registerPasswordPolicies.add(passwordHistoryValidator);
                    }
                }
            }
        }
        logger.info(
            String.format("registerPasswordPolicies: %s", registerPasswordPolicies.toString()));
    }

    @Override
    public Boolean isSatisfied(String password) throws Exception {
        if (policyMap == null || policyMap.isEmpty()) {
            logger.info("No password policy found. Skipping the policy builder");
            return true;
        }

        if (registerPasswordPolicies != null && registerPasswordPolicies.size() > 0) {

            Integer validCounter = 0;

            for (PasswordValidator passwordValidator : registerPasswordPolicies) {
                if (passwordValidator.isValid(password)) {
                    validCounter++;
                }
            }

            logger.info("validCounter: " + validCounter);
            if (validCounter == registerPasswordPolicies.size()) {
                return true;
            } else {
                throw new Exception("Password should match the policy: " + policyMap);
            }
        }

        return false;
    }
}