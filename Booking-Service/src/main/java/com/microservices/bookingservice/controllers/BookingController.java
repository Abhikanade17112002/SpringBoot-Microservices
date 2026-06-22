package com.microservices.bookingservice.controllers;
import com.microservices.bookingservice.dtos.request.BookingRequestDTO;
import com.microservices.bookingservice.dtos.response.BookingResponseDTO;
import com.microservices.bookingservice.enums.BookingStatus;
import com.microservices.bookingservice.services.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<String> testController() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( bookingService.createBooking(bookingRequestDTO) );
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookingResponseDTO>> getAllBookingsForAdmin(@RequestParam( name = "pageno" ,defaultValue = "0") int pageno, @RequestParam( name = "pagesize" , defaultValue = "10") int pagesize,@RequestParam(name = "sortby" , defaultValue = "bookingId") String sortby , @RequestParam(name = "asce" ,  defaultValue = "true") Boolean asce) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getAllBookingsForAdmin(pageno,pagesize,sortby,asce) );
    }

    @GetMapping("/customers")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Page<BookingResponseDTO>> getAllBookingsForCustomer(@RequestParam( name = "pageno" ,defaultValue = "0") int pageno, @RequestParam( name = "pagesize" , defaultValue = "10") int pagesize,@RequestParam(name = "sortby" , defaultValue = "bookingId") String sortby , @RequestParam(name = "asce" ,  defaultValue = "true") Boolean asce) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getAllBookingsForCustomer(pageno,pagesize,sortby,asce) );
    }

    @GetMapping("/owners")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Page<BookingResponseDTO>> getAllBookingsForOwners(@RequestParam( name = "pageno" ,defaultValue = "0") int pageno, @RequestParam( name = "pagesize" , defaultValue = "10") int pagesize,@RequestParam(name = "sortby" , defaultValue = "bookingId") String sortby , @RequestParam(name = "asce" ,  defaultValue = "true") Boolean asce) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getAllBookingsForOwner(pageno,pagesize,sortby,asce) );
    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','OWNER')")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable("bookingId") String bookingId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getBookingById(bookingId));
    }
    @PutMapping("/canclebooking/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<BookingResponseDTO> cancelBookingById(@PathVariable("bookingId") String bookingId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.cancleBookingById(bookingId) );
    }

    @PutMapping("/confirmbooking/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public ResponseEntity<BookingResponseDTO> confirmBookingById(@PathVariable("bookingId") String bookingId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.confirmBookingById(bookingId) );
    }
    @PutMapping("/checkin/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public ResponseEntity<BookingResponseDTO> checkInBookingById(@PathVariable("bookingId") String bookingId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.checkInBookingById(bookingId) );
    }
    @PutMapping("/checkout/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public ResponseEntity<BookingResponseDTO> checkOutBookingById(@PathVariable("bookingId") String bookingId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.checkOutBookingById(bookingId) );
    }
    @GetMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookingResponseDTO>> getBookingsByCustomerId(@PathVariable("customerId")String customerId, @RequestParam( name = "pageno" , defaultValue = "0") int pageno,@RequestParam( name = "pagesize" , defaultValue = "10") int pagesize,@RequestParam( name = "sortby" , defaultValue = "bookingId") String sortby, @RequestParam( name = "asce" , defaultValue = "true")Boolean asce) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getBookingsByCustomerId(customerId,pageno,pagesize,sortby,asce) );
    }
    @GetMapping("/hotels/{hotelId}")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public ResponseEntity<Page<BookingResponseDTO>> getBookingsByHotelId(@PathVariable("hotelId")String hotelId, @RequestParam( name = "pageno" , defaultValue = "0") int pageno,@RequestParam( name = "pagesize" , defaultValue = "10") int pagesize,@RequestParam( name = "sortby" , defaultValue = "bookingId") String sortby, @RequestParam( name = "asce" , defaultValue = "true")Boolean asce) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getBookingsByHotelId(hotelId,pageno,pagesize,sortby,asce) );
    }
    @GetMapping("/hotels/{hotelId}/status")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public ResponseEntity<Page<BookingResponseDTO>> getBookingsByHotelIdAndStatus(@PathVariable("hotelId")String hotelId, @RequestParam("status") BookingStatus status, @RequestParam( name = "pageno" , defaultValue = "0") int pageno, @RequestParam( name = "pagesize" , defaultValue = "10") int pagesize, @RequestParam( name = "sortby" , defaultValue = "bookingId") String sortby, @RequestParam( name = "asce" , defaultValue = "true")Boolean asce) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getBookingsByHotelIdAndStatus(hotelId,status,pageno,pagesize,sortby,asce) );
    }
    @GetMapping("/hotels/{hotelId}/between")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public ResponseEntity<Page<BookingResponseDTO>> getBookingsByHotelIdAndDatesBetween(@PathVariable("hotelId")String hotelId, @RequestParam(name = "startdate")LocalDate starteDate , @RequestParam(name = "enddate")LocalDate endDate, @RequestParam( name = "pageno" , defaultValue = "0") int pageno, @RequestParam( name = "pagesize" , defaultValue = "10") int pagesize, @RequestParam( name = "sortby" , defaultValue = "bookingId") String sortby, @RequestParam( name = "asce" , defaultValue = "true")Boolean asce) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getBookingsByHotelIdAndDatesBetween(hotelId,starteDate,endDate,pageno,pagesize,sortby,asce) );
    }
    @DeleteMapping("/softdelete/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingResponseDTO> softDeleteBookingById(@PathVariable("bookingId") String bookingId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.softDeleteBookingById(bookingId) );
    }

}
