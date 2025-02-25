package com.example.booking.Kafka;

import com.example.booking.DTO.Request.EmailRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailProducer emailProducer;

    public EmailController(EmailProducer emailProducer) {
        this.emailProducer = emailProducer;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        emailProducer.sendEmail(emailRequest);
        return "Email request sent to Kafka!";
    }
}