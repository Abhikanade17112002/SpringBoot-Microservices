package com.microservices.notificationservice.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.SesV2Client;


@Configuration
public class AwsConfiguration {


    private final String FROM_EMAIL_ID;
    private final String TO_EMAIL_ID;
    private final String REGION;

    public AwsConfiguration(
            @Value("${aws.ses.from-email-address}") String fromEmailId,@Value("${aws.ses.to-addresses}") String toEmailId,@Value("${aws.region}") String region) {
        this.FROM_EMAIL_ID = fromEmailId;
        TO_EMAIL_ID = toEmailId;
        REGION = region;
    }

    @Bean
    public SesV2Client sesV2Client() {
        return SesV2Client
                .builder()
                .region(Region.of(REGION))
                .build() ;
    }

}
