package com.microsercives.userservice.dtos.request;

import com.microsercives.userservice.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDate;

public class UpdateUserProfileRequestDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth ;
    private String emailId;

    public UpdateUserProfileRequestDTO(String userId, String firstName, String lastName, LocalDate dateOfBirth, String emailId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.emailId = emailId;
    }

    public UpdateUserProfileRequestDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "UpdateUserProfileRequestDTO{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
