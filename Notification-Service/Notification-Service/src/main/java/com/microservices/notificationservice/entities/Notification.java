package com.microservices.notificationservice.entities;

import com.microservices.notificationservice.enums.NotificationStatus;
import com.microservices.notificationservice.enums.NotificationType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table( name =  "notifications" ,
        indexes = {
                @Index(name = "idx_customer_id", columnList = "customerId"),
                @Index(name = "idx_booking_id", columnList = "bookingId"),
                @Index(name = "idx_recipient_email", columnList = "recipientEmailId"),
                @Index(name = "idx_notification_type", columnList = "notificationType"),
                @Index(name = "idx_notification_status", columnList = "notificationStatus")
        }
        )
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String notificationId ;

    @Column(nullable = false)
    private String customerId ;

    @Column(nullable = false)
    private String bookingId ;

    @Column(nullable = false)
    private String recipientEmailId ;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType ;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus ;

    @Column(nullable = false)
    private String subject ;

    @Column(nullable = false)
    private String message ;

    @Column(nullable = false)
    private LocalDateTime sentAt ;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt ;

    public Notification() {
    }

    public Notification(String notificationId, String customerId, String bookingId, String recipientEmailId, NotificationType notificationType, NotificationStatus notificationStatus, String subject, String message, LocalDateTime sentAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.notificationId = notificationId;
        this.customerId = customerId;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
        return "Notification{" +
                "notificationId='" + notificationId + '\'' +
                ", customerId='" + customerId + '\'' +
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

