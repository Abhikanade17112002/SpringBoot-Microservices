package com.microsercives.userservice.dtos.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadUserProfileImageRequestDTO {
    private MultipartFile file;

    public UploadUserProfileImageRequestDTO() {
    }

    public UploadUserProfileImageRequestDTO(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "UploadUserProfileImageRequestDTO{" +
                "file=" + file +
                '}';
    }
}
