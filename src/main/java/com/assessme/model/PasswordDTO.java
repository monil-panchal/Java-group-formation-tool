package com.assessme.model;

/**
 * @author: monil Created on: 2020-06-04
 */
public class PasswordDTO {

    private String password;

    public PasswordDTO() {
    }

    public PasswordDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
