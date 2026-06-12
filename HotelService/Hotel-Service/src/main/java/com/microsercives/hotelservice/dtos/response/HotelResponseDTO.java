package com.microsercives.hotelservice.dtos.response;

public class HotelResponseDTO {

    private String hotelId;

    private String hotelName;

    private String location;

    private String description;

    private String ownerId;

    private boolean active;

    public HotelResponseDTO() {
    }

    public HotelResponseDTO(String hotelId, String hotelName, String location, String description, String ownerId, boolean active) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.description = description;
        this.ownerId = ownerId;
        this.active = active;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "HotelResponseDTO{" +
                "hotelId='" + hotelId + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", active=" + active +
                '}';
    }
}