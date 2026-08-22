package com.microservices.notificationservice.controllers;


import com.microservices.notificationservice.dtos.NotificationResponseDTO;
import com.microservices.notificationservice.enums.NotificationStatus;
import com.microservices.notificationservice.enums.NotificationType;
import com.microservices.notificationservice.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Notification Service is up and running!";
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(@PathVariable(name = "notificationId") String notificationId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificationService.getNotificationById(notificationId));
    }
    @GetMapping("/my-notifications")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Page<NotificationResponseDTO>> getMyNotifications(@RequestParam(name = "pageno", defaultValue = "0") int pageno, @RequestParam(name = "pagesize", defaultValue = "10") int pagesize, @RequestParam(name = "sortby", defaultValue = "notificationId") String sortby, @RequestParam(name = "asce", defaultValue = "true") Boolean asce) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificationService.getMyNotifications(pageno, pagesize, sortby, asce));
    }

    @GetMapping("/by-staus/{notificationstatus}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Page<NotificationResponseDTO>> getNotificationsByStatus(@PathVariable(name = "notificationstatus")NotificationStatus notificationStatus , @RequestParam(name = "pageno", defaultValue = "0") int pageno, @RequestParam(name = "pagesize", defaultValue = "10") int pagesize, @RequestParam(name = "sortby", defaultValue = "notificationId") String sortby, @RequestParam(name = "asce", defaultValue = "true") Boolean asce){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificationService.getNotificationsByStatus(notificationStatus,pageno,pagesize,sortby,asce));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<NotificationResponseDTO>> getNotificationsByType(@PathVariable NotificationType type, @RequestParam(defaultValue = "0") int pageno, @RequestParam(defaultValue = "5") int pagesize, @RequestParam(defaultValue = "createdAt") String sortby, @RequestParam(defaultValue = "false") boolean ascending) {

        return ResponseEntity.ok(notificationService.getNotificationsByType(type, pageno, pagesize, sortby, ascending));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<NotificationResponseDTO>> getAllNotifications(@RequestParam(defaultValue = "0") int pageno, @RequestParam(defaultValue = "5") int pagesize, @RequestParam(defaultValue = "createdAt") String sortby, @RequestParam(defaultValue = "false") boolean ascending) {

        return ResponseEntity.ok(notificationService.getAllNotifications(pageno, pagesize, sortby, ascending));
    }

    @GetMapping("/user/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<NotificationResponseDTO>> getNotificationsByCustomerId(@PathVariable String customerId, @RequestParam(defaultValue = "0") int pageno, @RequestParam(defaultValue = "5") int pagesize, @RequestParam(defaultValue = "createdAt") String sortby, @RequestParam(defaultValue = "false") boolean ascending) {

        return ResponseEntity.ok(notificationService.getNotificationsByCustomerId(customerId, pageno, pagesize, sortby, ascending));
    }
}
