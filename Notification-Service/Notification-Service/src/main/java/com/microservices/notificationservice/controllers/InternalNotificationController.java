package com.microservices.notificationservice.controllers;

import com.microservices.notificationservice.dtos.NotificationRequestDTO;
import com.microservices.notificationservice.dtos.NotificationResponseDTO;
import com.microservices.notificationservice.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/notifications")
public class InternalNotificationController {
    @Autowired
    private final NotificationService notificationService;

    public InternalNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationResponseDTO> sendNotification(@Valid @RequestBody NotificationRequestDTO notificationRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificationService.sendNotification(notificationRequestDTO));
    }
}
