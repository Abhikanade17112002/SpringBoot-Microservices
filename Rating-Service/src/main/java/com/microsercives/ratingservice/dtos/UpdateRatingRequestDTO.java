package com.microsercives.ratingservice.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class UpdateRatingRequestDTO {
    @Min(1)
    @Max(5)
    private Integer rating;
    private String feedback;

    public UpdateRatingRequestDTO(Integer rating, String feedback) {
        this.rating = rating;
        this.feedback = feedback;
    }
    public UpdateRatingRequestDTO() {
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating( Integer rating) {
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
        return "UpdateRatingRequestDTO{" +
                "rating=" + rating +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
