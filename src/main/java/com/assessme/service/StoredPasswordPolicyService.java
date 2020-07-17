package com.assessme.service;

import java.util.Map;

/**
 * @author: monil Created on: 2020-06-17
 */
public interface StoredPasswordPolicyService {

    Map<String, Object> getPasswordPolicies() throws Exception;
}
