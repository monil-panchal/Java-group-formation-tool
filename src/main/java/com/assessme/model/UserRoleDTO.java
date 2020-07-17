package com.assessme.model;

import java.io.Serializable;
import java.util.Set;

/**
 * @author: monil Created on: 2020-05-29
 */
public class UserRoleDTO extends User implements Serializable {

    private Set<String> userRoles;

    public UserRoleDTO() {
        super();
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }


    @Override
    public String toString() {
        return "UserRoleDTO{" +
            "userRoles=" + userRoles +
            '}';
    }
}
