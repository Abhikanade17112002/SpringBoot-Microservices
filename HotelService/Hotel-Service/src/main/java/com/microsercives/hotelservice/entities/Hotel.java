package com.microsercives.hotelservice.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "hotels",
        indexes = {
                @Index(
                        name = "idx_hotel_name",
                        columnList = "hotelName"
                ),
                @Index(
                        name = "idx_hotel_location",
                        columnList = "location"
                ),
                @Index(
                        name = "idx_owner_id",
                        columnList = "ownerId"
                )
        }
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "hotelId"
)
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String hotelId;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER, mappedBy = "hotelId" )
    private List<HotelImage> hotelImages = new ArrayList<>();

    public Hotel() {
    }

    public Hotel(String hotelId, String hotelName, String location, String description, String ownerId, boolean active, List<HotelImage> hotelImages) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.description = description;
        this.ownerId = ownerId;
        this.active = active;
        this.hotelImages = hotelImages;
    }

    public List<HotelImage> getHotelImages() {
        return hotelImages;
    }

    public void setHotelImages(List<HotelImage> hotelImages) {
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

    @Override
    public String toString() {
        return "Hotel{" +
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