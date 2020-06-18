package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: monil
 * Created on: 2020-06-17
 */
@Repository
public class PasswordPoliciesDAOImpl implements PasswordPolicyDAO {

    private final Logger logger = LoggerFactory.getLogger(PasswordPoliciesDAOImpl.class);
    private final ConnectionManager connectionManager;

    public PasswordPoliciesDAOImpl() {
        connectionManager = new ConnectionManager();
    }


    @Override
    public Map<String, Object> getAllPasswordPolicies() throws Exception {
        // SQL query for fetching the all password policies
        String selectAllPoliciesQuery = "SELECT * FROM password_policy";

        HashMap<String, Object> policyMap = new HashMap<>();

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection.prepareStatement(selectAllPoliciesQuery)
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