package com.microsercives.hotelservice.dtos.response;

import java.util.ArrayList;
import java.util.List;

public class HotelResponseDTO {

    private String hotelId;

    private String hotelName;

    private String location;

    private String description;

    private String ownerId;

    private boolean active;

    private List<HotelImageResponseDTO> hotelImages = new ArrayList<>();

    public HotelResponseDTO() {
    }

    public HotelResponseDTO(String hotelId, String hotelName, String location, String description, String ownerId, boolean active, List<HotelImageResponseDTO> hotelImages) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.description = description;
        this.ownerId = ownerId;
        this.active = active;
        this.hotelImages = hotelImages;
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

    public List<HotelImageResponseDTO> getHotelImages() {
        return hotelImages;
    }

    public void setHotelImages(List<HotelImageResponseDTO> hotelImages) {
        this.hotelImages = hotelImages;
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
                ", hotelImages=" + hotelImages +
                '}';
    }
}