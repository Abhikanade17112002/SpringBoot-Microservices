package com.microservices.bookingservice.services.impl;

import com.microservices.bookingservice.dtos.request.BookingRequestDTO;
import com.microservices.bookingservice.dtos.response.BookingResponseDTO;
import com.microservices.bookingservice.entities.AuthenticatedUser;
import com.microservices.bookingservice.entities.Booking;
import com.microservices.bookingservice.enums.BookingStatus;
import com.microservices.bookingservice.repositories.BookingRepository;
import com.microservices.bookingservice.services.BookingService;
import com.microservices.bookingservice.services.internal.validation.BookingValidationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {
    private final static Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final BookingValidationService validationService;

    public BookingServiceImpl(ModelMapper modelMapper, BookingRepository bookingRepository, BookingValidationService validationService) {
        this.modelMapper = modelMapper;
        this.bookingRepository = bookingRepository;
        this.validationService = validationService;
    }

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( !validationService.validateCustomerIsActive(user.getUserId()))
        {
            logger.info("Customer With Id ==> {} Is Not Active", user.getUserId());
            return new BookingResponseDTO();
        }

        if( !validationService.validateHotelIsActive(bookingRequestDTO.getHotelId())){
            logger.info("Hotel With Id ==> {} Is Not Active", bookingRequestDTO.getHotelId());
            return new BookingResponseDTO();
        }

        Booking newBooking = new Booking();
        newBooking.setBookingStatus(BookingStatus.PENDING);
        newBooking.setActive(true);
        newBooking.setHotelId(bookingRequestDTO.getHotelId());
        newBooking.setCheckInDate(bookingRequestDTO.getCheckInDate());
        newBooking.setCheckOutDate(bookingRequestDTO.getCheckOutDate());
        newBooking.setCustomerId(user.getUserId());
        newBooking.setTotalPrice(bookingRequestDTO.getTotalPrice());
        Booking saveBooking = bookingRepository.save(newBooking);
        logger.info("Booking Created ==> {}", saveBooking);
        return modelMapper.map(saveBooking, BookingResponseDTO.class);
    }

    @Override
    public Page<BookingResponseDTO> getAllBookingsForAdmin(int pageno, int pagesize, String sortby, Boolean asce) {
        Sort sort = asce ? Sort.by(sortby).ascending() :  Sort.by(sortby).descending() ;
        Pageable page =  PageRequest.of(pageno, pagesize, sort);
        Page<Booking> bookings = bookingRepository.findAll(page);
        return bookings.map(booking -> modelMapper.map(booking, BookingResponseDTO.class));
    }

    @Override
    public Page<BookingResponseDTO> getAllBookingsForCustomer(int pageno, int pagesize, String sortby, Boolean asce) {
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Sort sort = asce ? Sort.by(sortby).ascending() :  Sort.by(sortby).descending() ;
        Pageable page =  PageRequest.of(pageno, pagesize, sort);
        Page<Booking> bookings = bookingRepository.findByCustomerId(user.getUserId(),page);
        return bookings.map(booking -> modelMapper.map(booking, BookingResponseDTO.class));
    }

    @Override
    public Page<BookingResponseDTO> getAllBookingsForOwner(int pageno, int pagesize, String sortby, Boolean asce) {
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> hotelIds = validationService.validateHotelOwnerHotelIdsList(user.getUserId()).getHotelIds();
        logger.info("Hotel Ids => {}", hotelIds);
        Sort sort = asce ? Sort.by(sortby).ascending() :  Sort.by(sortby).descending() ;
        Pageable page =  PageRequest.of(pageno, pagesize, sort);
        Page<Booking> bookings = bookingRepository.findByHotelIdIn(hotelIds,page);
        return bookings.map(booking -> modelMapper.map(booking, BookingResponseDTO.class));
    }
}
