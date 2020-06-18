package com.assessme.service;

import com.assessme.db.dao.PasswordPolicyDAO;
import com.assessme.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-06-17
 */
@Service
public class StoredPasswordPolicyServiceImpl implements StoredPasswordPolicyService {

    private Logger logger = LoggerFactory.getLogger(StoredPasswordPolicyServiceImpl.class);

    private PasswordPolicyDAO passwordPolicyDAO;

    public StoredPasswordPolicyServiceImpl(PasswordPolicyDAO passwordPolicyDAO) {
        this.passwordPolicyDAO = passwordPolicyDAO;
    }

    @Override
    public Map<String, Object> getPasswordPolicies() throws Exception {
        Map<String, Object> policyMap = null;
        try {
            policyMap = passwordPolicyDAO.getAllPasswordPolicies();

            String resMessage = String.format("Policies retrieved from the database: %s", policyMap);
            logger.info(resMessage);

        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the policies from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return policyMap;
    }
}
