package com.example.booking.Kafka;

import com.example.booking.DTO.Request.EmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailConsumer {
    private final JavaMailSender mailSender;

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void listen(EmailRequest emailRequest) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailRequest.getTo());
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getBody());
            mailSender.send(message);
            System.out.println("Email sent to: " + emailRequest.getTo());
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
