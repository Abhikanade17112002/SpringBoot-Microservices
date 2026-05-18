package com.microsercives.hotelservice.entities;

import jakarta.persistence.*;

@Entity
@Table( name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private String Id ;

    private String hotelName ;

    private String location ;

    private String description ;


    public Hotel(String id, String hotelName, String location, String description) {
        Id = id;
        this.hotelName = hotelName;
        this.location = location;
        this.description = description;
    }

    public Hotel() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
        return "Hotel{" +
                "Id='" + Id + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
