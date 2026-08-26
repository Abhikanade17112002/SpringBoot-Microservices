package com.microsercives.hotelservice.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "hotel_images")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "imageId"
)
public class HotelImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotelId;
    @Column(nullable = false)
    private String objectKey;
    @Column(nullable = false)
    private String originalFileName;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private Long fileSize;
    @Column(nullable = false)
    private Integer displayOrder;
    @Column(nullable = false)
    private boolean primaryImage;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(nullable = true)
    private String imagePublicUrl ;

    public HotelImage() {
    }

    public HotelImage(String imageId, Hotel hotelId, String objectKey, String originalFileName, String contentType, Long fileSize, Integer displayOrder, boolean primaryImage, LocalDateTime createdAt, LocalDateTime updatedAt, String imagePublicUrl) {
        this.imageId = imageId;
        this.hotelId = hotelId;
        this.objectKey = objectKey;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.displayOrder = displayOrder;
        this.primaryImage = primaryImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imagePublicUrl = imagePublicUrl;
    }

    public void setHotelId(Hotel hotelId) {
        this.hotelId = hotelId;
    }

    public String getImagePublicUrl() {
        return imagePublicUrl;
    }

    public void setImagePublicUrl(String imagePublicUrl) {
        this.imagePublicUrl = imagePublicUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Hotel getHotelId() {
        return hotelId;
    }

    public void setHotel(Hotel hotel) {
        this.hotelId = hotel;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(boolean primaryImage) {
        this.primaryImage = primaryImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "HotelImage{" +
                "imageId='" + imageId + '\'' +
                ", objectKey='" + objectKey + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                ", displayOrder=" + displayOrder +
                ", primaryImage=" + primaryImage +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", imagePublicUrl='" + imagePublicUrl + '\'' +
                '}';
    }
}
