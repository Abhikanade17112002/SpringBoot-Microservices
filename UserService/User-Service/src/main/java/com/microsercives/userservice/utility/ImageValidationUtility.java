package com.microsercives.userservice.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
public class ImageValidationUtility {
    private static final Logger logger = LoggerFactory.getLogger(ImageValidationUtility.class);
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png", "image/webp");
    //5 MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private ImageValidationUtility() {
    }

    public static void validate(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Profile image cannot be empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Profile image cannot exceed 5 MB");
        }

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            logger.error("Profile image content type {} is not supported", file.getContentType());
            throw new IllegalArgumentException("Only JPEG, PNG and WebP images are allowed");
        }
    }
}
