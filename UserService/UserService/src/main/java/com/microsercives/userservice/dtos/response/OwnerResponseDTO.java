package com.microsercives.userservice.dtos.response;

import com.microsercives.userservice.entities.User;

public class OwnerResponseDTO {
    private String ownerId ;
    private User user ;
    private boolean isActive ;

    public OwnerResponseDTO(String ownerId, User user, boolean isActive) {
        this.ownerId = ownerId;
        this.user = user;
        this.isActive = isActive;
    }

    public OwnerResponseDTO() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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
        return "OwnerResponseDTO{" +
                "ownerId='" + ownerId + '\'' +
                ", user=" + user +
                ", isActive=" + isActive +
                '}';
    }
}
