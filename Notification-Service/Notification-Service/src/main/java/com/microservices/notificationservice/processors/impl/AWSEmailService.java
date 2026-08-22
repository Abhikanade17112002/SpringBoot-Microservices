package com.microservices.notificationservice.processors.impl;

import com.microservices.notificationservice.processors.EmailProcessor;
import org.springframework.stereotype.Component;

@Component("awsEmail")
public class AWSEmailService implements EmailProcessor {
    @Override
    public String sendEmail(String recipientEmailId, String emailSubject, String messageBody) {
        // Implement the logic to send email using AWS SES
        return null;
    }
}
