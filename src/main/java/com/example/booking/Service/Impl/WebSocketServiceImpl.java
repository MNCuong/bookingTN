package com.example.booking.Service.Impl;

import com.example.booking.Service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendPaymentLink(String bookingId, String paymentUrl) {
        messagingTemplate.convertAndSend("/topic/payment/" + bookingId, paymentUrl);
    }
}


