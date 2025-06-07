package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.ContactMessageRequest;
import com.example.booking.Entity.ContactMessage;
import com.example.booking.Entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendCusSimpleMessageConfirmRegister(User user, String token);

    void sendVerificationEmail(User user);
    void replyEmail(ContactMessageRequest request);

    void sendBookingSuccessEmailWithQR(User user, String flightCode, String departureAirport, String arrivalAirport,
                                       LocalDateTime departureTime, BigDecimal totalAmount, byte[] qrImage);
}
