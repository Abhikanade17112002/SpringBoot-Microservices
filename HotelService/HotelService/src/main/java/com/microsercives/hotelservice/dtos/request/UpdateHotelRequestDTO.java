package com.microsercives.hotelservice.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateHotelRequestDTO {

    @NotBlank(message = "Hotel Name Cannot Be Empty")
    private String hotelName;

    @NotBlank(message = "Location Cannot Be Empty")
    private String location;

    @NotBlank(message = "Description Cannot Be Empty")
    private String description;

    public UpdateHotelRequestDTO() {
    }

    public UpdateHotelRequestDTO(String hotelName, String location, String description) {
        this.hotelName = hotelName;
        this.location = location;
        this.description = description;
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

    @Override
    public String toString() {
        return "UpdateHotelRequestDTO{" +
                "hotelName='" + hotelName + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}