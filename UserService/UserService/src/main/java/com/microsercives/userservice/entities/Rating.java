package com.microsercives.userservice.entities;

public class Rating {
    private String ratingId ;
    private String userId ;
    private String hotelId ;
    private int rating ;
    private String feedBack ;
    private Hotel hotel ;

    public Rating() {
    }

    public Rating(String ratingId, String userId, String hotelId, int rating, String feedBack, Hotel hotel) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.rating = rating;
        this.feedBack = feedBack;
        this.hotel = hotel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId='" + ratingId + '\'' +
                ", userId='" + userId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", rating=" + rating +
                ", feedBack='" + feedBack + '\'' +
                ", hotel=" + hotel +
                '}';
    }
}
