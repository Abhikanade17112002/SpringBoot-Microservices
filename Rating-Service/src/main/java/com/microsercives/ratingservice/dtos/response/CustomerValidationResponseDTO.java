package com.microsercives.ratingservice.dtos.response;

public class CustomerValidationResponseDTO {
    private String hotelId ;
    private Boolean isActive;

    public CustomerValidationResponseDTO(String hotelId, Boolean isActive) {
        this.hotelId = hotelId;
        this.isActive = isActive;
    }

    public CustomerValidationResponseDTO() {
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "HotelValidationResponseDTO{" +
                "hotelId='" + hotelId + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
