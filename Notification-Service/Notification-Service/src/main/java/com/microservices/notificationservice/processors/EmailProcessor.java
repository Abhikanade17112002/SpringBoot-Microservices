package com.microservices.notificationservice.processors;

public interface EmailProcessor {
    String sendEmail(String recipientEmailId, String emailSubject, String messageBody);
}

