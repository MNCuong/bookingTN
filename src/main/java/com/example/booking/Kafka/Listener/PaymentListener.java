
package com.example.booking.Kafka.Listener;

import com.example.booking.DTO.Event.BookingEvent;
import com.example.booking.DTO.Request.PayRequest;
import com.example.booking.Service.PaymentService;
import com.example.booking.Service.WebSocketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
@KafkaListener(topics = "booking_topic", groupId = "payment-group")
public class PaymentListener {
    private final PaymentService paymentService;
    private final WebSocketService webSocketService;


    @KafkaHandler
    public void handleBookingCreated(BookingEvent event) throws Exception {
        PayRequest payRequest = PayRequest.builder()
                .bookingId(event.getBookingId())
                .amount_raw(event.getTotalPrice())
                .typeService(event.getTypeService())
                .userEmail(event.getUserEmail())
                .build();
        log.info("amountPaymentListener:{}",event.getTotalPrice());

        // Gọi API thanh toán VNPAY
        String paymentUrl = paymentService.getPayKafka(payRequest);
        webSocketService.sendPaymentLink(event.getBookingId() + "", paymentUrl);

    }
}
