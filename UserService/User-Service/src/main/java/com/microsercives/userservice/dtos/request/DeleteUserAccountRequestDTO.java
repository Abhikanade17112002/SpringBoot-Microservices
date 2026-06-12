package com.microsercives.userservice.dtos.request;

public class DeleteUserAccountRequestDTO {
    private String userId ;
    private String password ;

    public DeleteUserAccountRequestDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public DeleteUserAccountRequestDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DeleteUserAccountRequestDTO{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
