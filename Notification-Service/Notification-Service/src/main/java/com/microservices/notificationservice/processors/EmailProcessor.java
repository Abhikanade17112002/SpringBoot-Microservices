package com.microservices.notificationservice.processors;

import com.microservices.notificationservice.dtos.SendEmailResponseDTO;
import software.amazon.awssdk.awscore.exception.AwsServiceException;

public interface EmailProcessor {
    SendEmailResponseDTO sendEmail(String recipientEmailId, String emailSubject, String messageBody);
}

