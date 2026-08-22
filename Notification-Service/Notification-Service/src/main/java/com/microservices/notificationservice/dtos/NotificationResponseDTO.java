package com.microservices.notificationservice.dtos;

import com.microservices.notificationservice.enums.NotificationStatus;
import com.microservices.notificationservice.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationResponseDTO {

    private String notificationId;

    private String userId;

    private String bookingId;

    private String recipientEmailId;

    private NotificationType notificationType;

    private NotificationStatus notificationStatus;

    private String subject;

    private String message;

    private LocalDateTime sentAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public NotificationResponseDTO() {
    }

    public NotificationResponseDTO(String notificationId, String userId, String bookingId, String recipientEmailId, NotificationType notificationType, NotificationStatus notificationStatus, String subject, String message, LocalDateTime sentAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.bookingId = bookingId;
        this.recipientEmailId = recipientEmailId;
        this.notificationType = notificationType;
        this.notificationStatus = notificationStatus;
        this.subject = subject;
        this.message = message;
        this.sentAt = sentAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
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

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "NotificationResponseDTO{" +
                "notificationId='" + notificationId + '\'' +
                ", userId='" + userId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", recipientEmailId='" + recipientEmailId + '\'' +
                ", notificationType=" + notificationType +
                ", notificationStatus=" + notificationStatus +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", sentAt=" + sentAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
