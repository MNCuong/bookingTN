package com.example.booking.Service;

import com.example.booking.Entity.User;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendCusSimpleMessageConfirmRegister(User user, String token);

    void sendVerificationEmail(User user);

}
