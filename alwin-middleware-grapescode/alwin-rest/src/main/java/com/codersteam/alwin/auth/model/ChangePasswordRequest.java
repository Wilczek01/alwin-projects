package com.codersteam.alwin.auth.model;

/**
 * @author Michal Horowic
 */
public class ChangePasswordRequest {

    private String password;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
