package com.microsercives.ratingservice.dtos.response;

public class GetHotelAverageRatingResponseDTO {
     private String hotelId;
     private int noOfRatings;
     private Double averageRating;
     public GetHotelAverageRatingResponseDTO() {}
     public GetHotelAverageRatingResponseDTO(String hotelId,  int noOfRatings, Double averageRating) {
            this.hotelId = hotelId;
            this.noOfRatings = noOfRatings;
            this.averageRating = averageRating;
     }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }


    public int getNoOfRatings() {
        return noOfRatings;
    }

    public void setNoOfRatings(int noOfRatings) {
        this.noOfRatings = noOfRatings;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "GetHotelAverageRatingResponseDTO{" +
                "hotelId='" + hotelId + '\'' +
                ", noOfRatings=" + noOfRatings +
                ", averageRating=" + averageRating +
                '}';
    }
}
