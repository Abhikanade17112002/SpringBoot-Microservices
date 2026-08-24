package com.microservices.notificationservice.services.impl;

import com.microservices.notificationservice.dtos.NotificationRequestDTO;
import com.microservices.notificationservice.dtos.NotificationResponseDTO;
import com.microservices.notificationservice.dtos.SendEmailResponseDTO;
import com.microservices.notificationservice.entities.AuthenticatedUser;
import com.microservices.notificationservice.entities.Notification;
import com.microservices.notificationservice.enums.NotificationStatus;
import com.microservices.notificationservice.enums.NotificationType;
import com.microservices.notificationservice.processors.EmailProcessor;
import com.microservices.notificationservice.repositories.NotificationRepository;
import com.microservices.notificationservice.services.NotificationService;
import com.microservices.notificationservice.utility.NotificationTemplateUtility;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.sesv2.model.TooManyRequestsException;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final EmailProcessor emailProcessor;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final NotificationTemplateUtility notificationTemplate ;

    public NotificationServiceImpl(NotificationRepository notificationRepository, EmailProcessor emailProcessor, ModelMapper modelMapper, NotificationTemplateUtility notificationTemplate) {
        this.notificationRepository = notificationRepository;
        this.emailProcessor = emailProcessor;
        this.modelMapper = modelMapper;
        this.notificationTemplate = notificationTemplate;
    }

    public NotificationResponseDTO sendNotification(NotificationRequestDTO notificationRequestDTO) {
        logger.info("Sending notification to recipient: {}", notificationRequestDTO.getRecipientEmailId());
        Notification notificationWithPendingStatus = getNotificationEntityWithPendingStatus(notificationRequestDTO);

            SendEmailResponseDTO response = emailProcessor.sendEmail(notificationWithPendingStatus.getRecipientEmailId(), notificationWithPendingStatus.getSubject(), notificationWithPendingStatus.getMessage());

            if(  response.getEmailSentSuccessfully() ){
                notificationWithPendingStatus.setNotificationStatus(NotificationStatus.SENT);
                Notification savedNotification = notificationRepository.save(notificationWithPendingStatus);
                return getNotificationResponseDTO(savedNotification);
            }
            else {
                return getNotificationResponseDTO(saveFailedNotification(notificationWithPendingStatus));
            }
    }

    @Override
    public NotificationResponseDTO getNotificationById(String notificationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
        if(authenticatedUser.getRole().equalsIgnoreCase("CUSTOMER") && !notification.getCustomerId().equals(authenticatedUser.getUserId())) {
            throw new RuntimeException("You are not authorized to access this notification");
        }
        return modelMapper.map(notification, NotificationResponseDTO.class);
    }

    @Override
    public Page<NotificationResponseDTO> getMyNotifications(int pageno, int pagesize, String sortby, Boolean asce) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        Sort sort = asce == true ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        Pageable page = PageRequest.of(pageno, pagesize, sort);
        Page<Notification> notifications = notificationRepository.findAllByCustomerId(authenticatedUser.getUserId(),page);
        return notifications.map((p)->modelMapper.map(p,NotificationResponseDTO.class));
    }

    @Override
    public Page<NotificationResponseDTO> getNotificationsByStatus(NotificationStatus notificationStatus, int pageno, int pagesize, String sortby, Boolean asce) {

        Sort sort = asce == true ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        Pageable page = PageRequest.of(pageno, pagesize, sort);
        Page<Notification> notifications = notificationRepository.findByNotificationStatus(notificationStatus,page);
        return notifications.map((p)->modelMapper.map(p,NotificationResponseDTO.class));
    }

    @Override
    public Page<NotificationResponseDTO> getNotificationsByType(NotificationType type, int pageno, int pagesize, String sortby, boolean asce) {
        Sort sort = asce == true ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        Pageable page = PageRequest.of(pageno, pagesize, sort);
        Page<Notification> notifications = notificationRepository.findByNotificationType(type,page);
        return notifications.map((p)->modelMapper.map(p,NotificationResponseDTO.class));
    }

    @Override
    public Page<NotificationResponseDTO> getAllNotifications(int pageno, int pagesize, String sortby, boolean asce ) {
        Sort sort = asce == true ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        Pageable page = PageRequest.of(pageno, pagesize, sort);
        Page<Notification> notifications = notificationRepository.findAll(page);
        return notifications.map((p)->modelMapper.map(p,NotificationResponseDTO.class));
    }

    @Override
    public Page<NotificationResponseDTO> getNotificationsByCustomerId(String customerId, int pageno, int pagesize, String sortby, boolean asce ) {
        Sort sort = asce == true ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        Pageable page = PageRequest.of(pageno, pagesize, sort);
        Page<Notification> notifications = notificationRepository.findAllByCustomerId(customerId,page);
        return notifications.map((p)->modelMapper.map(p,NotificationResponseDTO.class));
    }

    private NotificationResponseDTO getNotificationResponseDTO(Notification notification) {
        NotificationResponseDTO notificationResponseDTO = modelMapper.map(notification, NotificationResponseDTO.class);
        return notificationResponseDTO;
    }
    @Transactional
    private Notification saveFailedNotification(Notification notification) {
        notification.setNotificationStatus(NotificationStatus.FAILED);
        notification.setSentAt(java.time.LocalDateTime.now());
        Notification savedNotification = notificationRepository.save(notification);
        logger.info("Saved failed notification: {}", savedNotification);
        return savedNotification;
    }
    @Transactional
    private Notification getNotificationEntityWithPendingStatus(NotificationRequestDTO notificationRequest) {
        String subject = notificationTemplate.getSubject(notificationRequest.getNotificationType());

        String message = notificationTemplate.getMessage(notificationRequest.getNotificationType(), notificationRequest.getBookingId());

        Notification notification = new Notification();
        notification.setCustomerId(notificationRequest.getUserId());
        notification.setBookingId(notificationRequest.getBookingId());
        notification.setRecipientEmailId(notificationRequest.getRecipientEmailId());
        notification.setNotificationType(notificationRequest.getNotificationType());
        notification.setNotificationStatus(NotificationStatus.PENDING);
        notification.setSubject(subject);
        notification.setMessage(message);
        notification.setSentAt(java.time.LocalDateTime.now());

        Notification savedNotification = notificationRepository.save(notification);
        logger.info("Saved notification: {}", savedNotification);
        return savedNotification;
    }
}
