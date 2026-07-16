package com.microservices.bookingservice.scheduler;

import com.microservices.bookingservice.services.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BookingExpiryScheduler {
    private final BookingService bookingService;
    private final static Logger LOG = LoggerFactory.getLogger(BookingExpiryScheduler.class);

    public BookingExpiryScheduler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(fixedRate = 600000)
    public void checkPaymentExpiry() {
        LOG.info("PAYMENT EXPIRY SCHEDULER STARTED");
        bookingService.handleBookingExpiry();
        LOG.info("PAYMENT EXPIRY SCHEDULER FINISHED");
    }
}
