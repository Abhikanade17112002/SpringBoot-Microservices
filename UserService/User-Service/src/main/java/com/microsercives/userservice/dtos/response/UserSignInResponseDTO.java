package com.microsercives.userservice.dtos.response;

public class UserSignInResponseDTO {
    private String accessToken;

    private String tokenType;

    private String role;

    private String userId ;

    public UserSignInResponseDTO() {
    }

    public UserSignInResponseDTO(String accessToken, String tokenType, String role, String userId) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.role = role;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserSignInResponseDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", role='" + role + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
