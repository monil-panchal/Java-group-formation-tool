package com.assessme.auth.password.restriction;

import com.assessme.auth.password.validator.*;
import com.assessme.config.BeanConfig;
import com.assessme.model.UserPasswordHistory;
import com.assessme.service.StoredPasswordPolicyService;
import com.assessme.service.UserPasswordHistoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: monil
 * Created on: 2020-06-17
 */
public class PasswordChangePolicyImpl implements PasswordPolicy {

    private Logger logger = LoggerFactory.getLogger(PasswordChangePolicyImpl.class);

    private StoredPasswordPolicyService storedPasswordPolicyService;
    private UserPasswordHistoryServiceImpl userPasswordHistoryService;
    private Map<String, Object> policyMap;
    private List<PasswordValidator> registerPasswordPolicies;
    private List<UserPasswordHistory> userPasswordHistoryList;
    private Long userId;

    public PasswordChangePolicyImpl(Long userId) throws Exception {
        this.userId = userId;
        this.storedPasswordPolicyService = BeanConfig.getBean(StoredPasswordPolicyService.class);
        this.userPasswordHistoryService = BeanConfig.getBean(UserPasswordHistoryServiceImpl.class);
        this.init();
    }

    private void init() throws Exception {

        try {
            policyMap = storedPasswordPolicyService.getPasswordPolicies();
            logger.info(String.format("PolicyMap from service: %s", policyMap));
            if (policyMap == null) {
                logger.info("No password policy found. Skipping the policy builder");
            } else {
                addPasswordRestrictions();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void addPasswordRestrictions() {
        logger.info(String.format("Calling addPasswordRestrictions"));

        registerPasswordPolicies = new ArrayList<>();

        for (Map.Entry<String, Object> entry : policyMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if ("minUppercaseCharacters".equalsIgnoreCase(key) && value != null) {
                Integer minUpperCaseLength = Integer.valueOf((String) value);
                if (minUpperCaseLength > 0) {
                    PasswordValidator upperCaseValidator = new UpperCaseValidatorImpl(minUpperCaseLength);
                    registerPasswordPolicies.add(upperCaseValidator);
                }
            } else if ("minLowercaseCharacters".equalsIgnoreCase(key) && value != null) {
                Integer minLowerCaseLength = Integer.valueOf((String) value);
                if (minLowerCaseLength > 0) {
                    PasswordValidator lowPasswordValidator = new LowerCaseValidatorImpl(minLowerCaseLength);
                    registerPasswordPolicies.add(lowPasswordValidator);
                }
            } else if ("minPasswordLength".equalsIgnoreCase(key) && value != null) {
                Integer minPasswordLength = Integer.valueOf((String) value);
                if (minPasswordLength > 0) {
                    PasswordValidator minLengthValidator = new MinLengthValidatorImpl(minPasswordLength);
                    registerPasswordPolicies.add(minLengthValidator);
                }
            } else if ("maxPasswordLength".equalsIgnoreCase(key) && value != null) {
                Integer maxPasswordLength = Integer.valueOf((String) value);
                if (maxPasswordLength > 0) {
                    PasswordValidator maxLengthValidator = new MaxLengthValidatorImpl(maxPasswordLength);
                    registerPasswordPolicies.add(maxLengthValidator);
                }
            } else if ("blockSpecialCharacters".equalsIgnoreCase(key) && value != null) {
                String blockSpecialCharacterRegex = String.valueOf(value);
                PasswordValidator specialCharacterValidator = new DisallowSpecialCharacterValidatorImpl(blockSpecialCharacterRegex);
                registerPasswordPolicies.add(specialCharacterValidator);

            } else if ("minSpecialCharacters".equalsIgnoreCase(key) && value != null) {
                Integer minSpecialCharacters = Integer.valueOf((String) value);
                if (minSpecialCharacters > 0) {
                    PasswordValidator specialCharacterLengthValidator = new SpecialCharacterLengthValidatorImpl(minSpecialCharacters);
                    registerPasswordPolicies.add(specialCharacterLengthValidator);
                }
            }
            else if("passwordHistoryConstraint".equalsIgnoreCase(key) && value!=null){
                Integer lastPasswordConstraint = Integer.valueOf((String) value);
                if (lastPasswordConstraint > 0) {
                    userPasswordHistoryList = this.userPasswordHistoryService.getUserPasswordHistory(this.userId, lastPasswordConstraint);

                    if(userPasswordHistoryList ==null || userPasswordHistoryList.isEmpty()){
                        continue;
                    }
                    else{
                        PasswordValidator passwordHistoryValidator = new PasswordHistoryValidatorImpl(userPasswordHistoryList);
                        registerPasswordPolicies.add(passwordHistoryValidator);
                    }
                }
            }
        }
        logger.info(String.format("registerPasswordPolicies: %s", registerPasswordPolicies.toString()));
    }

    @Override
    public Boolean isSatisfied(String password) throws Exception {
        if (policyMap == null) {
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