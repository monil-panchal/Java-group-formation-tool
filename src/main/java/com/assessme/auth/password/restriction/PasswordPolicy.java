package com.assessme.auth.password.restriction;

/**
 * @author: monil Created on: 2020-06-17
 */
public interface PasswordPolicy {

    Boolean isSatisfied(String password) throws Exception;

}
