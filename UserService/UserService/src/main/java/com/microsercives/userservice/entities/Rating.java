package com.microsercives.userservice.entities;

public class Rating {
    private String userId ;
    private String hotelId ;
    private int rating ;
    private String feedBack ;

    public Rating() {
    }

    public Rating(String userId, String hotelId, int rating, String feedBack) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.rating = rating;
        this.feedBack = feedBack;
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

    @Override
    public String toString() {
        return "Rating{" +
                "userId='" + userId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", rating=" + rating +
                ", feedBack='" + feedBack + '\'' +
                '}';
    }
}
