package com.microservices.notificationservice.utility;

import com.microservices.notificationservice.enums.NotificationType;
import org.springframework.stereotype.Component;

@Component("notificationTemplateUtility")
public class NotificationTemplateUtility {

    public String getSubject(NotificationType type) {

        return switch (type) {



            case BOOKING_CONFIRMATION ->
                    "Booking Confirmed Successfully";

            case BOOKING_FAILED ->
                    "Booking Failed";

            case BOOKING_CANCELLATION ->
                    "Booking Cancelled";

            case BOOKING_REMINDER ->
                    "Reminder: Upcoming Booking";

            case BOOKING_EXPIRATION ->
                    "Booking Expired";

            case PAYMENT_SUCCESS ->
                    "Payment Successful";

            case PAYMENT_FAILURE ->
                    "Payment Failed";

            case PAYMENT_FAILURE_IN_RETRY_ATTEMPT ->
                    "Payment Retry Failed";

            case BOOKING_REFUND_SUCCESS ->
                    "Booking Refund Processed Successfully";

            case BOOKING_ALREADY_REFUNDED ->
                    "Booking Already Refunded";

            case BOOKING_ALREADY_CONFIRMED_NOT_ELIGIBLE_FOR_REFUND ->
                    "Booking Not Eligible for Refund";


            case BOOKING_CANCELLATION_NOT_ELIGIBLE_AS_BOOKING_NOT_CONFIRMED ->
                    "Booking Cannot Be Cancelled";

            case BOOKING_IN_PENDING_STATE_DUE_TO_PAYMENT_FAILURE_RETRY_ALLOWED ->
                    "Booking Payment Failed - Retry Available";

            case BOOKING_FAILED_DUE_TO_PAYMENT_SERVICE_FAILURE ->
                    "Booking Failed Due to Payment Service Error";

            case BOOKING_CANCELLATION_DUE_TO_MAX_ATTEMPTS_FOR_PAYMENT_EXHAUSTED ->
                    "Booking Cancelled After Payment Attempts Failed";

            case BOOKING_CANCELLATION_DUE_PAYMENT_WINDOW_EXPIRED ->
                    "Booking Cancelled - Payment Window Expired";

            case BOOKING_CHECKED_IN ->
                    "Check-In Successful";

            case BOOKING_CHECKED_OUT ->
                    "Check-Out Successful";
        };
    }

    public String getMessage(
            NotificationType type,
            String bookingId
    ) {

        return switch (type) {


            case BOOKING_CONFIRMATION ->
                    "Your booking with ID "
                            + bookingId
                            + " has been confirmed successfully. We look forward to hosting you.";

            case BOOKING_FAILED ->
                    "Unfortunately, your booking with ID "
                            + bookingId
                            + " could not be completed. Please try again or contact support if the issue persists.";

            case BOOKING_CANCELLATION ->
                    "Your booking with ID "
                            + bookingId
                            + " has been cancelled successfully.";

            case BOOKING_REMINDER ->
                    "This is a reminder that you have an upcoming booking with ID "
                            + bookingId
                            + ". Please review your booking details and complete any pending actions.";

            case BOOKING_EXPIRATION ->
                    "Your booking with ID "
                            + bookingId
                            + " has expired because the payment was not completed within the allowed payment window.";

            case PAYMENT_SUCCESS ->
                    "Your payment for booking "
                            + bookingId
                            + " was completed successfully. Your booking is being processed.";

            case PAYMENT_FAILURE ->
                    "The payment for booking "
                            + bookingId
                            + " could not be completed. Please retry the payment to continue with your booking.";

            case PAYMENT_FAILURE_IN_RETRY_ATTEMPT ->
                    "Your payment retry attempt for booking "
                            + bookingId
                            + " was unsuccessful. Please try again or use a different payment method.";

            case BOOKING_REFUND_SUCCESS ->
                    "The refund for booking "
                            + bookingId
                            + " has been processed successfully. Please allow the required processing time for the amount to reflect in your account.";

            case BOOKING_ALREADY_REFUNDED ->
                    "The refund for booking "
                            + bookingId
                            + " has already been processed. No further action is required.";

            case BOOKING_ALREADY_CONFIRMED_NOT_ELIGIBLE_FOR_REFUND ->
                    "Booking "
                            + bookingId
                            + " has already been confirmed and is not eligible for a refund under the current cancellation policy.";


            case BOOKING_CANCELLATION_NOT_ELIGIBLE_AS_BOOKING_NOT_CONFIRMED ->
                    "Booking "
                            + bookingId
                            + " cannot be cancelled because it has not yet been confirmed.";

            case BOOKING_IN_PENDING_STATE_DUE_TO_PAYMENT_FAILURE_RETRY_ALLOWED ->
                    "Booking "
                            + bookingId
                            + " is currently pending because the payment could not be completed. You may retry the payment to continue with the booking.";

            case BOOKING_FAILED_DUE_TO_PAYMENT_SERVICE_FAILURE ->
                    "Booking "
                            + bookingId
                            + " could not be completed because the payment service is currently unavailable. Please try again later.";

            case BOOKING_CANCELLATION_DUE_TO_MAX_ATTEMPTS_FOR_PAYMENT_EXHAUSTED ->
                    "Booking "
                            + bookingId
                            + " has been cancelled because the maximum number of payment attempts was exceeded.";

            case BOOKING_CANCELLATION_DUE_PAYMENT_WINDOW_EXPIRED ->
                    "Booking "
                            + bookingId
                            + " has been cancelled because the payment was not completed within the permitted payment window.";

            case BOOKING_CHECKED_IN ->
                    "You have successfully checked in for booking "
                            + bookingId
                            + ". We hope you enjoy your stay.";

            case BOOKING_CHECKED_OUT ->
                    "You have successfully checked out from booking "
                            + bookingId
                            + ". Thank you for staying with us.";
        };
    }
}