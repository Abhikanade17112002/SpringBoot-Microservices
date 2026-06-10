package com.microsercives.userservice.dtos.response;

public class CustomerValidationResponseDTO {
    private String customerId ;
    private Boolean isActive;

    public CustomerValidationResponseDTO(String customerId, Boolean isActive) {
        this.customerId = customerId;
        this.isActive = isActive;
    }

    public CustomerValidationResponseDTO() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "CustomerValidationResponseDTO{" +
                "customerId='" + customerId + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
