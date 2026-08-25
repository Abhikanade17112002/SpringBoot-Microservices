package com.microsercives.userservice.services;


import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {

    String upload(MultipartFile file, String objectKey);

    Boolean delete(String objectKey);

    String generatePresignedUrl(String objectKey);

}
