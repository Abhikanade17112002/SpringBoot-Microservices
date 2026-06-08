package com.microsercives.ratingservice.dtos.response;


public class RatingResponseDTO {

    private String ratingId;

    private String customerId;

    private String hotelId;

    private Integer rating;

    private String feedback;

    public RatingResponseDTO(String ratingId, String customerId, String hotelId, Integer rating, String feedback) {
        this.ratingId = ratingId;
        this.customerId = customerId;
        this.hotelId = hotelId;
        this.rating = rating;
        this.feedback = feedback;
    }

    public RatingResponseDTO() {
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "RatingResponseDTO{" +
                "ratingId='" + ratingId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", rating=" + rating +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}