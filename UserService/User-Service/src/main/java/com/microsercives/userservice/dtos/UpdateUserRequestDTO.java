package com.microsercives.userservice.dtos;

import java.time.LocalDate;

public class UpdateUserRequestDTO {

    private String firstName;

    private String lastName;

    private String emailId;

    private LocalDate dateOfBirth;

    public UpdateUserRequestDTO() {
    }

    public UpdateUserRequestDTO(String firstName, String lastName, String emailId, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.dateOfBirth = dateOfBirth;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}