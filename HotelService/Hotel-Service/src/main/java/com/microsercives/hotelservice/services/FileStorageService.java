package com.microsercives.hotelservice.services;


import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {

    String upload(MultipartFile file, String objectKey);

    Boolean delete(String objectKey);

    String getPublicUrlFromObjectKey(String objectKey);

}
