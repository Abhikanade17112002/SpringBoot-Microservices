package com.microservices.bookingservice.dtos.response;

import java.util.List;

public class ListOfHotelOwnerHotelIdsListResponseDTO {
    private List<String> hotelIds;
    public ListOfHotelOwnerHotelIdsListResponseDTO() {}
    public ListOfHotelOwnerHotelIdsListResponseDTO(List<String> hotelIds) {
        this.hotelIds = hotelIds;
    }

    public List<String> getHotelIds() {
        return hotelIds;
    }

    public void setHotelIds(List<String> hotelIds) {
        this.hotelIds = hotelIds;
    }

    @Override
    public String toString() {
        return "ListOfHotelOwnerHotelIdsListResponseDTO{" +
                "hotelIds=" + hotelIds +
                '}';
    }
}
