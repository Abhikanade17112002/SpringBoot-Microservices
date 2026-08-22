package com.microservices.bookingservice.clinets.notificationclients;

import com.microservices.bookingservice.dtos.request.NotificationRequestDTO;
import com.microservices.bookingservice.dtos.response.NotificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE-DEV" , path = "/api/v1/internal/notifications")
public interface NotificationClient {

    @PostMapping("/send")
    NotificationResponseDTO sendNotification(@RequestBody NotificationRequestDTO notificationRequestDTO);
}
