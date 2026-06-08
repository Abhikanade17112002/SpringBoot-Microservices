package com.microsercives.ratingservice.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateRatingRequestDTO {

    @NotBlank
    private String hotelId;
    @Min(1)
    @Max(5)
    private Integer rating;
    private String feedback;

    public CreateRatingRequestDTO(String hotelId, Integer rating, String feedback) {
        this.hotelId = hotelId;
        this.rating = rating;
        this.feedback = feedback;
    }

    public CreateRatingRequestDTO() {
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId( String hotelId) {
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
        return "CreateRatingRequestDTO{" +
                "hotelId='" + hotelId + '\'' +
                ", rating=" + rating +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}