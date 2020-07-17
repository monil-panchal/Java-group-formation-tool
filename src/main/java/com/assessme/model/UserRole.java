package com.assessme.model;

/**
 * @author: monil Created on: 2020-05-30
 */

import java.io.Serializable;
import java.util.Objects;

/**
 * Model bean of the user_role table of the database
 */
public class UserRole implements Serializable {

    private Long userId;
    private Integer roleId;

    public UserRole() {
    }

    public UserRole(Long userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
            "userId=" + userId +
            ", roleId='" + roleId + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRole)) {
            return false;
        }
        UserRole userRole = (UserRole) o;
        return getUserId().equals(userRole.getUserId()) &&
            getRoleId().equals(userRole.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getRoleId());
    }
}
