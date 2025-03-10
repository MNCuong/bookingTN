package com.example.booking.Service;

public interface WebSocketService {
    void sendPaymentLink(String bookingId, String paymentUrl);
}
