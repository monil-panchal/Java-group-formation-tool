package com.assessme.service;

import com.assessme.db.dao.PasswordPoliciesDAOImpl;
import com.assessme.db.dao.PasswordPolicyDAO;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: monil Created on: 2020-06-17
 */
@Service
public class StoredPasswordPolicyServiceImpl implements StoredPasswordPolicyService {

    private static StoredPasswordPolicyServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(StoredPasswordPolicyServiceImpl.class);
    private final PasswordPolicyDAO passwordPolicyDAO;

    public StoredPasswordPolicyServiceImpl() {
        this.passwordPolicyDAO = PasswordPoliciesDAOImpl.getInstance();
    }

    public static StoredPasswordPolicyServiceImpl getInstance() {
        if (instance == null) {
            instance = new StoredPasswordPolicyServiceImpl();
        }
        return instance;
    }

    @Override
    public Map<String, Object> getPasswordPolicies() throws Exception {
        Map<String, Object> policyMap = null;
        try {
            policyMap = passwordPolicyDAO.getAllPasswordPolicies();

            String resMessage = String
                .format("Policies retrieved from the database: %s", policyMap);
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
