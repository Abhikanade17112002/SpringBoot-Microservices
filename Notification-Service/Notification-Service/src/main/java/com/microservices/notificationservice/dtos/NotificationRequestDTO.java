package com.microservices.notificationservice.dtos;

import com.microservices.notificationservice.enums.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequestDTO {
    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotBlank(message = "Booking ID cannot be blank")
    private String bookingId;

    @NotBlank(message = "Recipient Email cannot be blank")
    @Email(message = "Invalid email format")
    private String recipientEmailId;

    @NotNull(message = "Notification Type cannot be null")
    private NotificationType notificationType;

    public NotificationRequestDTO() {
    }

    public NotificationRequestDTO(String userId, String bookingId, String recipientEmailId, NotificationType notificationType) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.recipientEmailId = recipientEmailId;
        this.notificationType = notificationType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRecipientEmailId() {
        return recipientEmailId;
    }

    public void setRecipientEmailId(String recipientEmailId) {
        this.recipientEmailId = recipientEmailId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public String toString() {
        return "NotificationRequestDTO{" +
                "userId='" + userId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", recipientEmailId='" + recipientEmailId + '\'' +
                ", notificationType=" + notificationType +
                '}';
    }
}
