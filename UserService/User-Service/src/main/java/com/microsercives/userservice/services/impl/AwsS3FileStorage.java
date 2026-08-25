package com.microsercives.userservice.services.impl;

import com.microsercives.userservice.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Service
public class AwsS3FileStorage implements FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(AwsS3FileStorage.class);
    private final S3Presigner s3Presigner;
    private final S3Client s3Client;
    private final String REGION;
    private final String BUCKET_NAME;

    public AwsS3FileStorage(S3Presigner s3Presigner, S3Client s3Client, @Value("${aws.region}") String REGION, @Value("${aws.s3.bucket-name}") String BUCKET_NAME) {
        this.s3Presigner = s3Presigner;
        this.s3Client = s3Client;
        this.REGION = REGION;
        this.BUCKET_NAME = BUCKET_NAME;
    }

    @Override
    public String upload(MultipartFile file, String objectKey) {

        try{
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(this.BUCKET_NAME)
                    .key(objectKey)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return objectKey;
        }
        catch (IOException exception){
            logger.error("ERROR OCCURRED IN UPLOAD FILE ${}",exception.getMessage());
            throw new RuntimeException("Unable to read uploaded file", exception);
        }
    }

    @Override
    public Boolean delete(String objectKey) {
       try{
           DeleteObjectRequest request = DeleteObjectRequest.builder()
                           .bucket(this.BUCKET_NAME)
                           .key(objectKey)
                           .build();

           DeleteObjectResponse response =  s3Client.deleteObject(request);
           return response.deleteMarker() ;
       } catch (Exception exception) {
           logger.error("ERROR OCCURRED IN DELETE FILE ${}",exception.getMessage());
           throw new RuntimeException(exception);
       }
    }

    @Override
    public String generatePresignedUrl(String objectKey) {

        try {
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(15))
                            .getObjectRequest(builder -> builder.bucket(this.BUCKET_NAME).key(objectKey))
                            .build();

            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toString();
        }
        catch (Exception exception) {
            logger.error("ERROR OCCURRED IN PRESIGN FILE URL ${}",exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}
