package com.microsercives.hotelservice.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class CreateHotelRequestDTO {

    @NotBlank(message = "Hotel Name Cannot Be Empty")
    private String hotelName;

    @NotBlank(message = "Location Cannot Be Empty")
    private String location;

    @NotBlank(message = "Description Cannot Be Empty")
    private String description;

    /*
        Required only when
        ROLE_ADMIN creates hotel.

        Ignored for ROLE_OWNER.
     */
    private String ownerId;

    public CreateHotelRequestDTO() {
    }

    public CreateHotelRequestDTO(String hotelName, String location, String description, String ownerId) {
        this.hotelName = hotelName;
        this.location = location;
        this.description = description;
        this.ownerId = ownerId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "CreateHotelRequestDTO{" +
                "hotelName='" + hotelName + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }
}