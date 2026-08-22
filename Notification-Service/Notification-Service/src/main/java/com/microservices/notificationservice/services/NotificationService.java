package com.microservices.notificationservice.services;

import com.microservices.notificationservice.dtos.NotificationRequestDTO;
import com.microservices.notificationservice.dtos.NotificationResponseDTO;
import com.microservices.notificationservice.enums.NotificationStatus;
import com.microservices.notificationservice.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    public NotificationResponseDTO sendNotification(NotificationRequestDTO notificationRequestDTO);

    NotificationResponseDTO getNotificationById(String notificationId);

    Page<NotificationResponseDTO> getMyNotifications(int pageno, int pagesize, String sortby, Boolean asce);

    Page<NotificationResponseDTO> getNotificationsByStatus(NotificationStatus notificationStatus, int pageno, int pagesize, String sortby, Boolean asce);

    Page<NotificationResponseDTO> getNotificationsByType(NotificationType type, int pageno, int pagesize, String sortby, boolean ascending);

    Page<NotificationResponseDTO> getAllNotifications(int pageno, int pagesize, String sortby, boolean ascending);

    Page<NotificationResponseDTO> getNotificationsByCustomerId(String customerId, int pageno, int pagesize, String sortby, boolean ascending);
}
