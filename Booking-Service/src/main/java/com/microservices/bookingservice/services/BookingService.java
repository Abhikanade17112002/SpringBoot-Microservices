package com.microservices.bookingservice.services;

import com.microservices.bookingservice.dtos.request.BookingRequestDTO;
import com.microservices.bookingservice.dtos.response.BookingResponseDTO;
import org.springframework.data.domain.Page;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO);
    Page<BookingResponseDTO> getAllBookingsForAdmin(int pageno, int pagesize, String sortby, Boolean asce);
    Page<BookingResponseDTO> getAllBookingsForCustomer(int pageno, int pagesize, String sortby, Boolean asce);
    Page<BookingResponseDTO> getAllBookingsForOwner(int pageno, int pagesize, String sortby, Boolean asce);
}
