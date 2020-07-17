package com.assessme.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author: monil Created on: 2020-06-16
 */
public class UserPasswordHistory implements Serializable {

    private Long userId;
    private String password;
    private Timestamp modifiedOn;

    public UserPasswordHistory() {
    }

    public UserPasswordHistory(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public UserPasswordHistory(Long userId, String password, Timestamp modifiedOn) {
        this.userId = userId;
        this.password = password;
        this.modifiedOn = modifiedOn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public String toString() {
        return "UserPasswordHistoryDAO{" +
            "userId=" + userId +
            ", password='" + password + '\'' +
            ", modifiedOn=" + modifiedOn +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPasswordHistory)) {
            return false;
        }
        UserPasswordHistory that = (UserPasswordHistory) o;
        return getUserId().equals(that.getUserId()) &&
            getPassword().equals(that.getPassword()) &&
            getModifiedOn().equals(that.getModifiedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getPassword(), getModifiedOn());
    }
}
