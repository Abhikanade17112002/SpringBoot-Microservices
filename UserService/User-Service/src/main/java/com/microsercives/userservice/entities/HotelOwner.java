package com.microsercives.userservice.entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table( name = "hotel_owners")
public class HotelOwner {
    @Id
    private String ownerId ;
    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn(name = "ownerId" , referencedColumnName = "userId")
    @MapsId
    private User user ;
    private boolean isActive = false ;

    public HotelOwner() {
    }

    public HotelOwner(String ownerId, User user, boolean isActive) {
        this.ownerId = ownerId;
        this.user = user;
        this.isActive = isActive;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "HotelOwner{" +
                "ownerId='" + ownerId + '\'' +
                ", user=" + user +
                ", isActive=" + isActive +
                '}';
    }
}
