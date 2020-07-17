package com.assessme.db.dao;

import java.util.Map;

/**
 * @author: monil Created on: 2020-06-17
 */
public interface PasswordPolicyDAO {

    Map<String, Object> getAllPasswordPolicies() throws Exception;
}
