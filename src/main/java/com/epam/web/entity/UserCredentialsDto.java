package com.epam.web.entity;

public class UserCredentialsDto {

    private final String username;
    private final String password;
    private final String passwordConfirmation;

    public UserCredentialsDto(String username, String password, String passwordConfirmation) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}
