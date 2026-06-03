package com.microsercives.userservice.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private String customerId;
    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn(name = "customerId", referencedColumnName = "userId")
    @MapsId
    private User user ;
    private boolean isActive = false;

    public Customer() {
    }

    public Customer(String customerId, User user, boolean isActive) {
        this.customerId = customerId;
        this.user = user;
        this.isActive = isActive;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", user=" + user +
                ", isActive=" + isActive +
                '}';
    }
}
