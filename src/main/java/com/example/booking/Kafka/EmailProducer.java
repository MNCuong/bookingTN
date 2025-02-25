package com.example.booking.Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import com.example.booking.DTO.Request.EmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailProducer {
    private final KafkaTemplate<String, EmailRequest> kafkaTemplate;
    public void sendEmail(EmailRequest emailRequest) {
        kafkaTemplate.send("email-topic", emailRequest);
    }
}
