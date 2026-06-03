package com.microsercives.userservice.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserSignInRequestDTO {
    @Email(message = "Email Id Should Follow Standard Format")
    @NotBlank
    private String emailId;

    @NotBlank( message = "Password Cannot Be Blank")
    private String password;

    public UserSignInRequestDTO() {
    }

    public UserSignInRequestDTO(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserSignInRequestDTO{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
