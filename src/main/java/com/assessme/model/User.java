package com.assessme.model;

import java.io.Serializable;

/**
 * @author: monil
 * Created on: 2020-05-29
 */

/**
 * Model bean of the user table of the database
 */
public class User implements Serializable {

    private int userId;
    private String bannerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isActive;

    public User() {
    }

    public User(String bannerId, String firstName, String lastName, String email, String password, Boolean isActive) {
        this.bannerId = bannerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
}
