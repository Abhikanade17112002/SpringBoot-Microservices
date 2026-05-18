package com.microsercives.hotelservice.dtos;

public class CreateHotelRequestDTO {

    private String hotelName;

    private String location;

    private String description;

    public CreateHotelRequestDTO() {
    }

    public CreateHotelRequestDTO(String hotelName,
                                 String location,
                                 String description) {
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
        return "CreateHotelRequestDTO{" +
                "hotelName='" + hotelName + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}