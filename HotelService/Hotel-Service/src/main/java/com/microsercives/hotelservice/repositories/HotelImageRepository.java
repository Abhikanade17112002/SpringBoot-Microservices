package com.microsercives.hotelservice.repositories;

import com.microsercives.hotelservice.entities.Hotel;
import com.microsercives.hotelservice.entities.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage,String> {

    List<HotelImage> findByHotelIdOrderByDisplayOrderAsc(Hotel hotelId);

    Optional<HotelImage> findByImageIdAndHotelId(String imageId, Hotel hotelId);

    Optional<HotelImage> findByHotelIdAndPrimaryImageTrue(Hotel hotelId);

    long countByHotelId(Hotel hotelId );
}
