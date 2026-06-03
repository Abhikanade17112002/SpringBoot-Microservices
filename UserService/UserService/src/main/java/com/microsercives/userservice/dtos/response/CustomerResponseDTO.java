package com.microsercives.userservice.dtos.response;

import com.microsercives.userservice.entities.User;

public class CustomerResponseDTO {
    private String customerId;
    private User user ;
    private boolean isActive ;

    public CustomerResponseDTO(String customerId, User user, boolean isActive) {
        this.customerId = customerId;
        this.user = user;
        this.isActive = isActive;
    }

    public CustomerResponseDTO() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "CustomerResponseDTO{" +
                "customerId='" + customerId + '\'' +
                ", user=" + user +
                ", isActive=" + isActive +
                '}';
    }
}
