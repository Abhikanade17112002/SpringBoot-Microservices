package com.microsercives.hotelservice.entities;

public class AuthenticatedUser {
    private String userId;
    private String emailId;
    private String role ;

    public AuthenticatedUser(String userId, String emailId, String role) {
        this.userId = userId;
        this.emailId = emailId;
        this.role = role;
    }

    public AuthenticatedUser() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthenticatedUser{" +
                "userId='" + userId + '\'' +
                ", emailId='" + emailId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
