package com.microsercives.hotelservice.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AwsConfigurations {
    private final String REGION;
    private final String BUCKET_NAME;

    public AwsConfigurations(@Value("${aws.region}") String REGION, @Value("${aws.s3.bucket-name}") String BUCKET_NAME) {
        this.REGION = REGION;
        this.BUCKET_NAME = BUCKET_NAME;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(this.REGION))
                .build();
    }
    @Bean
    public S3Presigner s3Presigner() {

        return S3Presigner.builder()
                .region(Region.of(this.REGION))
                .build();
    }
}
