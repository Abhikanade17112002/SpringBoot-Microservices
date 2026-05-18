package com.microsercives.hotelservice.dtos;

public class HotelResponseDTO {

    private String id;

    private String hotelName;

    private String location;

    private String description;

    public HotelResponseDTO() {
    }

    public HotelResponseDTO(String id,
                            String hotelName,
                            String location,
                            String description) {
        this.id = id;
        this.hotelName = hotelName;
        this.location = location;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "HotelResponseDTO{" +
                "id='" + id + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}