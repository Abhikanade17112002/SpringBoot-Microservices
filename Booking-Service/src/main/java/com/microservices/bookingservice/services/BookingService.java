package com.microservices.bookingservice.services;

import com.microservices.bookingservice.dtos.request.BookingRequestDTO;
import com.microservices.bookingservice.dtos.response.BookingResponseDTO;
import com.microservices.bookingservice.enums.BookingStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO);
    Page<BookingResponseDTO> getAllBookingsForAdmin(int pageno, int pagesize, String sortby, Boolean asce);
    Page<BookingResponseDTO> getAllBookingsForCustomer(int pageno, int pagesize, String sortby, Boolean asce);
    Page<BookingResponseDTO> getAllBookingsForOwner(int pageno, int pagesize, String sortby, Boolean asce);
    BookingResponseDTO getBookingById(String bookingId);
    BookingResponseDTO cancleBookingById(String bookingId);
    BookingResponseDTO confirmBookingById(String bookingId);
    BookingResponseDTO checkInBookingById(String bookingId);
    BookingResponseDTO checkOutBookingById(String bookingId);
    Page<BookingResponseDTO> getBookingsByCustomerId(String customerId,int pageno, int pagesize, String sortby, Boolean asce);
    Page<BookingResponseDTO> getBookingsByHotelId(String hotelId, int pageno, int pagesize, String sortby, Boolean asce);
    Page<BookingResponseDTO> getBookingsByHotelIdAndStatus(String hotelId, BookingStatus status, int pageno, int pagesize, String sortby, Boolean asce);
    Page<BookingResponseDTO> getBookingsByHotelIdAndDatesBetween(String hotelId, LocalDate starteDate, LocalDate endDate, int pageno, int pagesize, String sortby, Boolean asce);
    BookingResponseDTO softDeleteBookingById(String bookingId);
    BookingResponseDTO retryBookingWithId(String bookingId);
}
