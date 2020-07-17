package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: monil Created on: 2020-06-17
 */
public class PasswordPoliciesDAOImpl implements PasswordPolicyDAO {

    private static PasswordPoliciesDAOImpl instance;
    final String selectAllPoliciesQuery = "SELECT * FROM password_policy";
    private final Logger logger = LoggerFactory.getLogger(PasswordPoliciesDAOImpl.class);
    private final ConnectionManager connectionManager;

    public PasswordPoliciesDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    public static PasswordPoliciesDAOImpl getInstance() {
        if (instance == null) {
            instance = new PasswordPoliciesDAOImpl();
        }
        return instance;
    }

    @Override
    public Map<String, Object> getAllPasswordPolicies() throws Exception {
        HashMap<String, Object> policyMap = new HashMap<>();
        try (
            Connection connection = connectionManager.getDBConnection().get();
            PreparedStatement preparedStatement = connection
                .prepareStatement(selectAllPoliciesQuery)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                policyMap.put(resultSet.getString("policy"), resultSet.getString("value"));
            }
            logger.info(String.format("Policies retrieved from the database: %s", policyMap));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return policyMap;
    }
}