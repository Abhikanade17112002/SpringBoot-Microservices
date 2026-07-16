package com.microservices.bookingservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.bookingservice.clinets.paymentclients.InternalPaymentClient;
import com.microservices.bookingservice.configurations.BookingPaymentProperties;
import com.microservices.bookingservice.dtos.request.BookingRequestDTO;
import com.microservices.bookingservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.bookingservice.dtos.response.*;
import com.microservices.bookingservice.entities.AuthenticatedUser;
import com.microservices.bookingservice.entities.Booking;
import com.microservices.bookingservice.enums.BookingStatus;
import com.microservices.bookingservice.enums.PaymentStatus;
import com.microservices.bookingservice.exception.exceptions.*;
import com.microservices.bookingservice.repositories.BookingRepository;
import com.microservices.bookingservice.services.BookingService;
import com.microservices.bookingservice.services.internal.paymentservice.PaymentService;
import com.microservices.bookingservice.services.internal.validation.BookingValidationService;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class BookingServiceImpl implements BookingService {
    private final static Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final BookingValidationService validationService;
    private final BookingPaymentProperties paymentProperties ;
    private final InternalPaymentClient paymentClient ;
    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;

    public BookingServiceImpl(ModelMapper modelMapper, BookingRepository bookingRepository, BookingValidationService validationService, BookingPaymentProperties paymentProperties, InternalPaymentClient paymentClient, ObjectMapper objectMapper, ObjectMapper objectMapper1, PaymentService paymentService) {
        this.modelMapper = modelMapper;
        this.bookingRepository = bookingRepository;
        this.validationService = validationService;
        this.paymentProperties = paymentProperties;
        this.paymentClient = paymentClient;
        this.objectMapper = objectMapper1;
        this.paymentService = paymentService;
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
        List<BookingStatus>  excludeBookingStatus  = List.of(BookingStatus.FAILED,BookingStatus.CANCELLED);
        Optional<Booking> existingBooking = bookingRepository.findByHotelIdAndCustomerIdAndCheckInDateAndCheckOutDateAndBookingStatusNotIn(bookingRequestDTO.getHotelId(), user.getUserId(), bookingRequestDTO.getCheckInDate(), bookingRequestDTO.getCheckOutDate(), excludeBookingStatus);

        if( existingBooking.isPresent() ) {
            logger.info("Booking With Customer Id  ==> {} , Check In Date {} And Check Out Date {} Exists", user.getUserId(), bookingRequestDTO.getCheckInDate(), bookingRequestDTO.getCheckOutDate());
            BookingResponseDTO response =  modelMapper.map(existingBooking.get(), BookingResponseDTO.class);
            response.setMessage("Booking Already Exists");
            return response;
        }
        Booking initialPendingSavedBooking = getSavedInitialBookingWithPendingStatus(bookingRequestDTO,user.getUserId());
        logger.info("Booking initialPendingSavedBooking Created ==> {}", initialPendingSavedBooking);
        CreatePaymentRequestDTO createPaymentRequest = createPaymentRequestDTO(initialPendingSavedBooking);
        try{
            InternalPaymentResponseDTO paymentResult  = paymentService.processPayment(createPaymentRequest);
            logger.info("Payment Result ==> {}", paymentResult);
            return createBookingResponseDTO(paymentResult,initialPendingSavedBooking);
        }
        catch (Exception exception) {
            saveFailedBooking(initialPendingSavedBooking);
            throw exception;
        }
    }

    @Override
    @Transactional
    public BookingResponseDTO retryBookingWithId(String bookingId) {
        Booking reterivedBooking = bookingRepository.findById(bookingId).orElseThrow(()-> new EntityNotFoundException("Booking with Id ==> " + bookingId + " Not Found"));
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( !reterivedBooking.getCustomerId().equals(user.getUserId()) ){
            throw new RuntimeException("Customer With Id ==> " + user.getUserId() + " Not Authorized for modifying this Booking");
        }
        if( !reterivedBooking.getBookingStatus().equals(BookingStatus.PENDING)){
            throw new RuntimeException("Booking With Id ==> " + bookingId + " Is either Completed or Cancelled");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = reterivedBooking.getPaymentExpiryTime() ;
        long timeLeft = Math.max( 0 , Duration.between(now,expiry).toMinutes());
        int attemptsLeft = Math.max( 0 , paymentProperties.getMaxAttempts() - reterivedBooking.getPaymentAttemptCount() ) ;
        logger.info("Attempt Left ==> {}", attemptsLeft);
        logger.info("Time Left ==> {}", timeLeft);
        if(  timeLeft <= 0 ){
            logger.info("Time Left ==> {}", timeLeft);
            return getCancelledBookingResponseDTO(reterivedBooking,"Booking cancelled because the payment window has expired.");
        }
        if(attemptsLeft <= 0){
            logger.info("Attempts Left ==> {}", attemptsLeft);
            return getCancelledBookingResponseDTO(reterivedBooking,"Booking cancelled because the maximum payment retry attempts have been exhausted.");
        }
        int attemptCount = reterivedBooking.getPaymentAttemptCount() + 1 ;
        logger.info("Attempt Count ==> {}", attemptCount);
        BookingResponseDTO response = null;
        try{
            PaymentResponseDTO paymentResult  = paymentService.retryPaymentByBookingId(bookingId);
            logger.info("Payment Result ==> {}", paymentResult);
            return getBookingResponseDTO(attemptCount,reterivedBooking,paymentResult);
        }
        catch (Exception e){
            logger.error("Exception Occurred ==> {}", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public BookingRefundResponseDTO refundBookingWithId(String bookingId) {
        Booking reterivedBooking = bookingRepository.findById(bookingId).orElseThrow(()-> new EntityNotFoundException("Booking with id " + bookingId + " not found."));
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if( user.getRole().equals("CUSTOMER") && !user.getUserId().equals(reterivedBooking.getCustomerId()) ){
            throw new RuntimeException("Customer With Id ==> " +  reterivedBooking.getCustomerId() + " Not Allowed to Refund This Booking");
        }
        if( reterivedBooking.getBookingStatus().equals(BookingStatus.REFUNDED)){
            reterivedBooking.setActive(false);
            bookingRepository.save(reterivedBooking);
            throw new PaymentAlreadyRefundedException("Payment Already Refunded");
        }
        if (reterivedBooking.getBookingStatus() != BookingStatus.CONFIRMED){
            throw new RuntimeException("Booking With Id ==> " +   reterivedBooking.getBookingId() + " Not Allowed to Refund In Non Confirmed Status" );
        }
        try {
            InternalPaymentResponseDTO paymentResponse  =  paymentService.processBookingRefundWithId(bookingId);
            return getBookingRefundByResponseDTO(reterivedBooking, paymentResponse);
        } catch (Exception exception) {
            throw exception;
        }
    }


    @Override
    public PaymentResponseDTO getPaymentByBookingId(String bookingId) {
        try {
           return paymentService.getPaymentByBookingId(bookingId);
        }
        catch (FeignException exception){
           throw exception;
        }
    }

    @Override
    public Page<BookingResponseDTO> getBookingsByBookingStatus(BookingStatus status, int pageno, int pagesize, String sortby, Boolean asce) {
        Sort sort = asce ? Sort.by(sortby).ascending() :  Sort.by(sortby).descending() ;
        Pageable page =  PageRequest.of(pageno, pagesize, sort);
        Page<Booking> bookings = bookingRepository.findAllByBookingStatus(status,page);
        return bookings.map(booking -> modelMapper.map(booking, BookingResponseDTO.class));
    }

    @Override
    @Transactional
    public void handleBookingExpiry() {
        List<Booking> expiredBookings = bookingRepository.findByBookingStatusAndPaymentExpiryTimeBefore(BookingStatus.PENDING, LocalDateTime.now());
        logger.info(" handleBookingExpiry STARTED");
        logger.info("EXPIRED BOOKING COUNT ==> {}", expiredBookings.size());
        if( expiredBookings.size() > 0){
            for (Booking booking : expiredBookings) {
                logger.info("EXPIRED BOOKING ==> {} ,  {} ", booking.getBookingId() , booking.getBookingStatus() );
                booking.setBookingStatus(BookingStatus.CANCELLED);
                booking.setActive(false);
            }
            bookingRepository.saveAll(expiredBookings);
        }
        // TODO:
        // Publish BookingExpired event
        // or call Notification Service to send expiry email.
        logger.info("{} BOOKINGS WERE CANCELLED BY SCHEDULER", expiredBookings.size());
        logger.info(" handleBookingExpiry FINISHED");
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

    @Override
    public BookingResponseDTO getBookingById(String bookingId) {
       Booking retreivedBooking  = bookingRepository.findById(bookingId).orElseThrow(()-> new EntityNotFoundException("Booking With Id ==> " + bookingId + " Not Found"));
       return modelMapper.map(retreivedBooking, BookingResponseDTO.class);
    }

    @Override
    public BookingResponseDTO cancleBookingById(String bookingId) {
        Booking reterivedBooking = bookingRepository.findById(bookingId).orElseThrow(()-> new EntityNotFoundException("Booking with Id ==> " + bookingId + " Not Found"));

        if( !reterivedBooking.getBookingStatus().equals(BookingStatus.CONFIRMED)){
            logger.info("Booking with Id ==> {} Is Not In Confirmed Status . Current Status {} ", bookingId,reterivedBooking.getBookingStatus());
            return modelMapper.map(reterivedBooking, BookingResponseDTO.class);
        }
        try {
            InternalPaymentResponseDTO paymentResponse  =  paymentService.processBookingRefundWithId(bookingId);
            return getBookingResponseDTO(reterivedBooking, paymentResponse);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @Override
    public BookingResponseDTO confirmBookingById(String bookingId) {
        Booking reterivedBooking = bookingRepository.findById(bookingId).orElseThrow(()-> new EntityNotFoundException("Booking with Id ==> " + bookingId + " Not Found"));
        if( reterivedBooking.getBookingStatus().equals(BookingStatus.PENDING) ){
            reterivedBooking.setBookingStatus(BookingStatus.CONFIRMED);
            reterivedBooking = bookingRepository.save(reterivedBooking);
        }
        return modelMapper.map(reterivedBooking, BookingResponseDTO.class);
    }
    @Override
    public BookingResponseDTO checkInBookingById(String bookingId) {
        Booking reterivedBooking = bookingRepository.findById(bookingId).orElseThrow(()-> new EntityNotFoundException("Booking with Id ==> " + bookingId + " Not Found"));
        if(!validationService.validateHotelIsActive(reterivedBooking.getHotelId())){
            logger.info("Hotel with Id ==> {} Is Not Active ", reterivedBooking.getHotelId());
            throw  new RuntimeException("Hotel With Id " + reterivedBooking.getHotelId() + " Is Not Active ");
        }

        if( reterivedBooking.getBookingStatus().equals(BookingStatus.CONFIRMED) ){
            reterivedBooking.setBookingStatus(BookingStatus.CHECKED_IN);
            reterivedBooking = bookingRepository.save(reterivedBooking);
        }
        return modelMapper.map(reterivedBooking, BookingResponseDTO.class);
    }

    @Override
    public BookingResponseDTO checkOutBookingById(String bookingId) {
        Booking reterivedBooking = bookingRepository.findById(bookingId).orElseThrow(()-> new EntityNotFoundException("Booking with Id ==> " + bookingId + " Not Found"));

        if(!validationService.validateHotelIsActive(reterivedBooking.getHotelId())){
            logger.info("Hotel with Id ==> {} Is Not Active ", reterivedBooking.getHotelId());
            throw  new RuntimeException("Hotel With Id " + reterivedBooking.getHotelId() + " Is Not Active ");
        }

        if( reterivedBooking.getBookingStatus().equals(BookingStatus.CHECKED_IN) ){
            reterivedBooking.setBookingStatus(BookingStatus.CHECKED_OUT);
            reterivedBooking = bookingRepository.save(reterivedBooking);
        }
        return modelMapper.map(reterivedBooking, BookingResponseDTO.class);
    }

    @Override
    public Page<BookingResponseDTO> getBookingsByCustomerId(String customerId,int pageno, int pagesize, String sortby, Boolean asce) {
        Sort sort = asce ? Sort.by(sortby).ascending() :  Sort.by(sortby).descending() ;
        Pageable page =  PageRequest.of(pageno, pagesize, sort);
        Page<Booking> bookings = bookingRepository.findByCustomerId(customerId,page);
        return bookings.map(booking -> modelMapper.map(booking, BookingResponseDTO.class));
    }

    @Override
    public Page<BookingResponseDTO> getBookingsByHotelId(String hotelId, int pageno, int pagesize, String sortby, Boolean asce) {

        if(!validationService.validateHotelIsActive(hotelId)){
            logger.info("Hotel with Id ==> {} Is Not Active ", hotelId);
            throw  new RuntimeException("Hotel With Id " + hotelId + " Is Not Active ");
        }

        Sort sort = asce ? Sort.by(sortby).ascending() :  Sort.by(sortby).descending() ;
        Pageable page =  PageRequest.of(pageno, pagesize, sort);
        Page<Booking> bookings = bookingRepository.findByHotelId(hotelId,page);
        return bookings.map(booking -> modelMapper.map(booking, BookingResponseDTO.class));
    }

    @Override
    public Page<BookingResponseDTO> getBookingsByHotelIdAndStatus(String hotelId, BookingStatus status, int pageno, int pagesize, String sortby, Boolean asce) {
        Sort sort = asce ? Sort.by(sortby).ascending() :  Sort.by(sortby).descending() ;
        Pageable page =  PageRequest.of(pageno, pagesize, sort);
        Page<Booking> bookings = bookingRepository.findByHotelIdAndBookingStatus(hotelId,status,page);
        return bookings.map(booking -> modelMapper.map(booking, BookingResponseDTO.class));
    }

    @Override
    public Page<BookingResponseDTO> getBookingsByHotelIdAndDatesBetween(String hotelId, LocalDate starteDate, LocalDate endDate, int pageno, int pagesize, String sortby, Boolean asce) {
        Sort sort = asce ? Sort.by(sortby).ascending() :  Sort.by(sortby).descending() ;
        Pageable page =  PageRequest.of(pageno, pagesize, sort);
        Page<Booking> bookings = bookingRepository.findByHotelIdAndCheckInDateBetween(hotelId,starteDate,endDate,page);
        return bookings.map(booking -> modelMapper.map(booking, BookingResponseDTO.class));
    }

    @Override
    public BookingResponseDTO softDeleteBookingById(String bookingId) {
        Booking reterivedBooking = bookingRepository.findById(bookingId).orElseThrow(()->new EntityNotFoundException("Booking with Id ==> " + bookingId + " Not Found"));
        if( reterivedBooking.isActive() ){
            reterivedBooking.setActive(false);
            reterivedBooking = bookingRepository.save(reterivedBooking);
        }
        return  modelMapper.map(reterivedBooking, BookingResponseDTO.class);
    }


    
    @Transactional
    private Booking getSavedInitialBookingWithPendingStatus(BookingRequestDTO bookingRequestDTO,String customerId){
        Booking newBooking = new Booking();
        newBooking.setBookingStatus(BookingStatus.PENDING);
        newBooking.setActive(true);
        newBooking.setPaymentMethod(bookingRequestDTO.getPaymentMethod());
        newBooking.setHotelId(bookingRequestDTO.getHotelId());
        newBooking.setCheckInDate(bookingRequestDTO.getCheckInDate());
        newBooking.setCheckOutDate(bookingRequestDTO.getCheckOutDate());
        newBooking.setCustomerId(customerId);
        newBooking.setTotalPrice(bookingRequestDTO.getTotalPrice());
        newBooking.setPaymentAttemptCount(1);
        newBooking.setPaymentExpiryTime(LocalDateTime.now().plusMinutes( paymentProperties.getPaymentExpiryTime().toMinutes() ));
        return bookingRepository.save(newBooking);
    }
    private CreatePaymentRequestDTO createPaymentRequestDTO(Booking  booking){
        CreatePaymentRequestDTO createPaymentRequest = new CreatePaymentRequestDTO();
        createPaymentRequest.setBookingId(booking.getBookingId());
        createPaymentRequest.setAmount(booking.getTotalPrice());
        createPaymentRequest.setCustomerId(booking.getCustomerId());
        createPaymentRequest.setPaymentMethod(booking.getPaymentMethod());
        return createPaymentRequest;
    }
    @Transactional
    private BookingResponseDTO createBookingResponseDTO(InternalPaymentResponseDTO paymentResult , Booking initialPendingSavedBooking){
        BookingResponseDTO response = null ;
        if( paymentResult.getPaymentStatus() == PaymentStatus.SUCCESS ){
            initialPendingSavedBooking.setBookingStatus(BookingStatus.CONFIRMED);
            response = modelMapper.map(bookingRepository.save(initialPendingSavedBooking), BookingResponseDTO.class);
            response.setMessage(paymentResult.getMessage());
            return  response;
        }
        else{
            String retryMessage = "Payment Cannot Be Completed Reason ==> " + paymentResult.getMessage() ;
            response = modelMapper.map(initialPendingSavedBooking, BookingResponseDTO.class);
            response.setMessage(retryMessage);
            response.setRetryAllowed(true);
            return  response;
        }
    }
    private BookingResponseDTO getBookingResponseDTO(int attemptCount, Booking reterivedBooking,PaymentResponseDTO paymentResult){
        BookingResponseDTO response = null;
        if( paymentResult.getPaymentStatus() == PaymentStatus.SUCCESS ){
            reterivedBooking.setBookingStatus(BookingStatus.CONFIRMED);
            reterivedBooking.setPaymentAttemptCount(attemptCount);
            response = modelMapper.map(bookingRepository.save(reterivedBooking), BookingResponseDTO.class);
            response.setMessage(paymentResult.getMessage());
            return  response;
        }
        else{
            String retryMessage = "Payment Cannot Be Completed Reason ==> " + paymentResult.getMessage() ;
            reterivedBooking.setPaymentAttemptCount(attemptCount);
            response = modelMapper.map(bookingRepository.save(reterivedBooking), BookingResponseDTO.class);
            response.setMessage(retryMessage);
            response.setRetryAllowed(true);
            return  response;
        }
    }
    private BookingResponseDTO getCancelledBookingResponseDTO(Booking reterivedBooking,String message) {
        reterivedBooking.setBookingStatus(BookingStatus.CANCELLED);
        reterivedBooking.setActive(false);
        Booking updatedBooking = bookingRepository.save(reterivedBooking);
        logger.info("Booking Updated ==> {}", updatedBooking);
        BookingResponseDTO response = modelMapper.map(updatedBooking, BookingResponseDTO.class);
        response.setMessage(message);
        response.setRetryAllowed(false);
        return  response;
    }

    private void saveFailedBooking(Booking booking) {
        booking.setBookingStatus(BookingStatus.FAILED);
        booking.setActive(false);
        bookingRepository.save(booking);
        return ;
    }

    private BookingRefundResponseDTO getBookingRefundByResponseDTO(Booking reterivedBooking ,InternalPaymentResponseDTO paymentResponse) {
        BookingRefundResponseDTO response = null ;
        if(  paymentResponse.getPaymentStatus() == PaymentStatus.REFUNDED ){
            reterivedBooking.setBookingStatus(BookingStatus.REFUNDED);
            response = modelMapper.map(bookingRepository.save(reterivedBooking), BookingRefundResponseDTO.class);
            response.setMessage(paymentResponse.getMessage());
            return  response;
        }
        else {
            response = modelMapper.map(reterivedBooking, BookingRefundResponseDTO.class);
            response.setMessage(paymentResponse.getMessage());
            return  response;
        }
    }

    private BookingResponseDTO getBookingResponseDTO(Booking reterivedBooking ,InternalPaymentResponseDTO paymentResponse) {
        BookingResponseDTO response = null ;
        if(  paymentResponse.getPaymentStatus() == PaymentStatus.REFUNDED ){
            reterivedBooking.setBookingStatus(BookingStatus.CANCELLED);
            response = modelMapper.map(bookingRepository.save(reterivedBooking), BookingResponseDTO.class);
            response.setMessage(paymentResponse.getMessage());
            return  response;
        }
        else {
            response = modelMapper.map(reterivedBooking, BookingResponseDTO.class);
            response.setMessage(paymentResponse.getMessage());
            return  response;
        }
    }
}
