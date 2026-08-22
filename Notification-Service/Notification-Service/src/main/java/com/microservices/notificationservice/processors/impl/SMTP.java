package com.microservices.notificationservice.processors.impl;

import com.microservices.notificationservice.processors.EmailProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("smtp")
@Primary
public class SMTP implements EmailProcessor {
    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(SMTP.class);

    public SMTP(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public String sendEmail(String recipientEmailId, String emailSubject, String messageBody) {
        logger.info("========== SMTP EMAIL ==========");
        logger.info("TO       : {}", recipientEmailId);
        logger.info("SUBJECT  : {}", emailSubject);
        logger.info("MESSAGE  : {}", messageBody);
        logger.info("================================");

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(recipientEmailId);
        mailMessage.setSubject(emailSubject);
        mailMessage.setText(messageBody);

        mailSender.send(mailMessage);

        return "true";
    }
}
