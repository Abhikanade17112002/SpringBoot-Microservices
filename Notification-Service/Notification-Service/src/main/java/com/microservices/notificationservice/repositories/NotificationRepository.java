package com.microservices.notificationservice.repositories;

import com.microservices.notificationservice.entities.Notification;
import com.microservices.notificationservice.enums.NotificationStatus;
import com.microservices.notificationservice.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {


    Page<Notification> findByNotificationStatus(NotificationStatus status, Pageable pageable);

    Page<Notification> findByNotificationType(NotificationType type, Pageable pageable);

    Page<Notification> findAllByCustomerId(String customerId,Pageable page);
}
