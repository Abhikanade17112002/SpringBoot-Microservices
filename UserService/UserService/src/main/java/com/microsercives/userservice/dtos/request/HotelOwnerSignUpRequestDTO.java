package com.microsercives.userservice.dtos.request;

import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class HotelOwnerSignUpRequestDTO {

    @NotBlank(message = "First Name Cannot Be Blank")
    private String firstName;
    @NotBlank(message = "Last Name Cannot Be Blank")
    private String lastName;
    @Past
    private LocalDate dateOfBirth;
    @Email(message = "Email Id Should Have Standard Format")
    @NotBlank
    private String emailId;
    @NotBlank(message = "Password Cannot Be Blank")
    private String password;

    public HotelOwnerSignUpRequestDTO() {
    }

    public HotelOwnerSignUpRequestDTO(String firstName, String lastName, LocalDate dateOfBirth, String emailId, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.emailId = emailId;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "HotelOwnerSignUpRequestDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
