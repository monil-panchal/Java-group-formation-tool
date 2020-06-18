package com.assessme.auth.password.restriction;

/**
 * @author: monil
 * Created on: 2020-06-17
 */
public interface PasswordPolicy {

    void addPasswordRestrictions();
    Boolean isSatisfied(String password) throws Exception;

}
