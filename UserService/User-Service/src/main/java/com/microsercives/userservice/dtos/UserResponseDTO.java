package com.microsercives.userservice.dtos;

import com.microsercives.userservice.enums.Role;
import java.time.LocalDate;

public class UserResponseDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth ;
    private String userName;
    private String emailId;
    private Role role;

    public UserResponseDTO(String userId, String firstName, String lastName, LocalDate dateOfBirth, String userName, String emailId, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.emailId = emailId;
        this.role = role;
    }

    public UserResponseDTO() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", userName='" + userName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", role=" + role +
                '}';
    }
}