package com.assessme.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: monil Created on: 2020-05-29
 */

/**
 * Model bean of the user table of the database
 */
public class User implements Serializable {

    private Long userId;
    private String bannerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isActive;

    public User() {
    }

    public User(String bannerId, String firstName, String lastName, String email, String password,
        Boolean isActive) {
        this.bannerId = bannerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId=" + userId +
            ", bannerId='" + bannerId + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", isActive=" + isActive +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return getUserId().equals(user.getUserId()) &&
            getBannerId().equals(user.getBannerId()) &&
            getFirstName().equals(user.getFirstName()) &&
            getLastName().equals(user.getLastName()) &&
            getEmail().equals(user.getEmail()) &&
            getPassword().equals(user.getPassword()) &&
            isActive.equals(user.isActive);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(getUserId(), getBannerId(), getFirstName(), getLastName(), getEmail(),
                getPassword(),
                isActive);
    }
}
