package com.microservices.bookingservice.controllers;
import com.microservices.bookingservice.dtos.request.BookingRequestDTO;
import com.microservices.bookingservice.dtos.response.BookingResponseDTO;
import com.microservices.bookingservice.services.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<BookingResponseDTO>> getAllBookingsFor(@RequestParam( name = "pageno" ,defaultValue = "0") int pageno, @RequestParam( name = "pagesize" , defaultValue = "10") int pagesize,@RequestParam(name = "sortby" , defaultValue = "bookingId") String sortby , @RequestParam(name = "asce" ,  defaultValue = "true") Boolean asce) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookingService.getAllBookingsForOwner(pageno,pagesize,sortby,asce) );
    }
}
