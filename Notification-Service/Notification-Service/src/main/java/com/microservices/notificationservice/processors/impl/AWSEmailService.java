package com.microservices.notificationservice.processors.impl;

import com.microservices.notificationservice.dtos.SendEmailResponseDTO;
import com.microservices.notificationservice.processors.EmailProcessor;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

@Component("awsEmail")
@Primary
public class AWSEmailService implements EmailProcessor {

    private final static Logger logger = LoggerFactory.getLogger(AWSEmailService.class);
    private final SesV2Client sesV2Client;
    private final String FROM_EMAIL_ID ;

    public AWSEmailService(SesV2Client sesV2Client,@Value("${aws.ses.from-email-address}") String fromEmailId ) {
        this.sesV2Client = sesV2Client;
        FROM_EMAIL_ID = fromEmailId;
    }

    @Override
    @CircuitBreaker(name = "send-email-cb" , fallbackMethod = "sendEmailFallBackMethode" )
    @Retry(name = "send-email-retry")
    public SendEmailResponseDTO sendEmail(String recipientEmailId, String emailSubject, String messageBody) {

        Destination destination = Destination
                .builder()
                .toAddresses(recipientEmailId)
                .build();
        Content subjectContent = Content.builder()
                .data(emailSubject)
                .charset("UTF-8")
                .build() ;
        Content bodyContent = Content.builder()
                .data(messageBody)
                .charset("UTF-8")
                .build() ;
        Body body = Body
                   .builder()
                   .text(bodyContent)
                   .build();
        Message message = Message
                          .builder()
                .subject(subjectContent)
                          .body(body)
                          .build();
        EmailContent emailContent = EmailContent.builder()
                                   .simple(message)
                                    .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                                        .fromEmailAddress(FROM_EMAIL_ID)
                                        .destination(destination)
                                        .content(emailContent)
                                        .build();
/*        throw new IllegalArgumentException("TEST NON RETRYABLE EXCEPTION");*//*
        throw SdkClientException.create("TEST RETRYABLE EXCEPTION");*/
        SendEmailResponse emailResponse = sesV2Client.sendEmail(emailRequest);
        logger.info("Email sent successfully. Message ID ==> {}", emailResponse.messageId());
        return new SendEmailResponseDTO(true,emailResponse.messageId(),emailSubject,messageBody);

    }

    public SendEmailResponseDTO sendEmailFallBackMethode(String recipientEmailId, String emailSubject, String messageBody , Exception exception) {

        logger.info("EXCEPTION OCCURRED ${}",exception.getMessage() );

        if( exception instanceof TooManyRequestsException ){
            logger.info("Thrown TOO MANY REQUESTS EXCEPTION ERROR ${} ",exception.getMessage() );
        }
        else if( exception instanceof SdkClientException ){
            logger.info("Thrown SDK EXCEPTION ERROR ${} ",exception.getMessage() );
        }
        else if (exception instanceof AwsServiceException ) {
            logger.info("Thrown AWS-SERVICE-EXCEPTION ERROR ${} ",exception.getMessage() );
        }
        else{
            logger.info("Thrown  ERROR ${} ",exception.getMessage() );
        }
        return new SendEmailResponseDTO(false,null,emailSubject,messageBody);
    }
}
