package com.example.booking.Service.Impl;

import com.example.booking.Entity.User;
import com.example.booking.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private RedisService verificationService;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cuongll9103@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendCusSimpleMessageConfirmRegister(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cuongm912003@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Account Verification");
        message.setText("Hi " + user.getFullName() + ",\n\nPlease click the link below to verify your account:\n\n"
                + "https://master-filly-mostly.ngrok-free.app/bookingBE-MNC/api/v1/auth/verify?userId=" + user.getId() + "&token=" + token);
        emailSender.send(message);


    }

    public void sendVerificationEmail(User user) {
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setTokenExpiryDate(new Date(System.currentTimeMillis() + 60000));
        verificationService.saveVerificationToken(user.getId().toString(), token);

        // Send email
        sendCusSimpleMessageConfirmRegister(user, token);
    }
}


