package com.microsercives.userservice.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserPasswordRequestDTO {

    @NotBlank(message = "User Id Cannot Be Blank")
    private String userId ;
    @NotBlank(message = "New Password Cannot Be Blank")
    private String newPassword ;
    @NotBlank(message = "Old Password Cannot Be Blank")
    private String oldPassword ;


    public UpdateUserPasswordRequestDTO(String userId, String newPassword, String oldPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public UpdateUserPasswordRequestDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String toString() {
        return "UpdateUserPasswordRequestDTO{" +
                "userId='" + userId + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                '}';
    }
}
