package com.assessme.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: monil Created on: 2020-06-04
 */

/**
 * Model bean of the user token. Can be used as a general person time based token
 */
public class UserToken implements Serializable {

    private Long userId;
    private String token;


    public UserToken() {
    }

    public UserToken(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserToken{" +
            "userId=" + userId +
            ", token='" + token + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserToken)) {
            return false;
        }
        UserToken userToken = (UserToken) o;
        return getUserId().equals(userToken.getUserId()) &&
            getToken().equals(userToken.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getToken());
    }
}

