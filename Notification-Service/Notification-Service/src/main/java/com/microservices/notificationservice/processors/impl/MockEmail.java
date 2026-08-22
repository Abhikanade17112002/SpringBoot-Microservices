package com.microservices.notificationservice.processors.impl;

import com.microservices.notificationservice.processors.EmailProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("mockEmail")
public class MockEmail implements EmailProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MockEmail.class);
    @Override
    public String sendEmail(String recipientEmailId, String emailSubject, String messageBody) {
        logger.info("========== MOCK EMAIL ==========");
        logger.info("TO       : {}", recipientEmailId);
        logger.info("SUBJECT  : {}", emailSubject);
        logger.info("MESSAGE  : {}", messageBody);
        logger.info("================================");
        return "";
    }
}
