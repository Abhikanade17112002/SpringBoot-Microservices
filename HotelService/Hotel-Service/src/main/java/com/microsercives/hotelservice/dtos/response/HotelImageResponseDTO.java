package com.microsercives.hotelservice.dtos.response;

public class HotelImageResponseDTO {

    private String imageId;

    private String imagePublicUrl;

    private Integer displayOrder;

    private boolean primaryImage;

    public HotelImageResponseDTO() {
    }

    public HotelImageResponseDTO(String imageId, String imagePublicUrl, Integer displayOrder, boolean primaryImage) {
        this.imageId = imageId;
        this.imagePublicUrl = imagePublicUrl;
        this.displayOrder = displayOrder;
        this.primaryImage = primaryImage;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImagePublicUrl() {
        return imagePublicUrl;
    }

    public void setImagePublicUrl(String imagePublicUrl) {
        this.imagePublicUrl = imagePublicUrl;
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

    @Override
    public String toString() {
        return "HotelImageResponseDTO{" +
                "imageId='" + imageId + '\'' +
                ", imagePublicUrl='" + imagePublicUrl + '\'' +
                ", displayOrder=" + displayOrder +
                ", primaryImage=" + primaryImage +
                '}';
    }
}
