package com.assessme.model;

import java.io.Serializable;
import java.util.Set;

/**
 * @author: monil
 * Created on: 2020-05-29
 */
public class UserRole extends User implements Serializable {

    private Set userRoles;

    public UserRole() {
        super();
    }

    public UserRole(Set userRoles) {
        this.userRoles = userRoles;
    }

}
