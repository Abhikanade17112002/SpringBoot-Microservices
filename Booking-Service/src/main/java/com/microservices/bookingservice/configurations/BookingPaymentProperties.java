package com.microservices.bookingservice.configurations;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "booking.payment")
public class BookingPaymentProperties {

    private int maxAttempts ;
    private Duration paymentExpiryTime ;

    public BookingPaymentProperties() {
    }

    public BookingPaymentProperties(int maxAttempts, Duration paymentExpiryTime) {
        this.maxAttempts = maxAttempts;
        this.paymentExpiryTime = paymentExpiryTime;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public Duration getPaymentExpiryTime() {
        return paymentExpiryTime;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
    public void setPaymentExpiryTime(Duration paymentExpiryTime) {
        this.paymentExpiryTime = paymentExpiryTime;
    }

    @Override
    public String toString() {
        return "BookingPaymentProperties{" +
                "maxAttempts=" + maxAttempts +
                ", paymentExpiryTime=" + paymentExpiryTime +
                '}';
    }
}
