package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.RegisterRequest;
import com.example.booking.DTO.Request.UserProfileRequest;
import com.example.booking.DTO.Response.UserResponse;
import com.example.booking.Entity.User;
import com.example.booking.Entity.UserProfile;
import com.example.booking.Exception.BookingException;
import com.example.booking.Repository.UserRepository;
import com.example.booking.Service.EmailService;
import com.example.booking.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageCommon messageCommon;
    private final EmailService emailService;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BookingException(ServiceMessageConstants.EMAIL_EXIST, messageCommon.getMessage(ServiceMessageConstants.EMAIL_EXIST));
        }
        if (userRepository.existsByPhone(registerRequest.getPhone_number())) {
            throw new BookingException(ServiceMessageConstants.PHONE_EXIST, messageCommon.getMessage(ServiceMessageConstants.PHONE_EXIST));
        }
        User savedUser = userRepository.save(User.builder()
                .phone(registerRequest.getPhone_number())
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFull_name())
                .createdAt(LocalDateTime.now())
                .roles("USER")
                .build());
        emailService.sendVerificationEmail(savedUser);
        return UserResponse.builder()
                .phone_number(registerRequest.getPhone_number())
                .email(registerRequest.getEmail())
                .full_name(registerRequest.getFull_name())
                .created_at(savedUser.getCreatedAt())
                .role(savedUser.getRoles())
                .build();
    }

    @Override
    public UserResponse registerHotel(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BookingException(ServiceMessageConstants.EMAIL_EXIST, messageCommon.getMessage(ServiceMessageConstants.EMAIL_EXIST));
        }
        if (userRepository.existsByPhone(registerRequest.getPhone_number())) {
            throw new BookingException(ServiceMessageConstants.PHONE_EXIST, messageCommon.getMessage(ServiceMessageConstants.PHONE_EXIST));
        }
        User savedUser = userRepository.save(User.builder()
                .phone(registerRequest.getPhone_number())
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFull_name())
                .createdAt(LocalDateTime.now())
                .roles("ADMIN")
                .build());
        emailService.sendVerificationEmail(savedUser);

        return UserResponse.builder()
                .phone_number(registerRequest.getPhone_number())
                .email(registerRequest.getEmail())
                .full_name(registerRequest.getFull_name())
                .created_at(savedUser.getCreatedAt())
                .role(savedUser.getRoles())
                .build();
    }

    public boolean verifyUser(String token) {
        Optional<User> user = userRepository.findByVerificationToken(token);
        if (user.isPresent()) {
            User verifiedUser = user.get();
            if (verifiedUser.getTokenExpiryDate().after(new Date())) {
                verifiedUser.setVerified(true);
                verifiedUser.setVerificationToken(null);
                verifiedUser.setTokenExpiryDate(null);
                userRepository.save(verifiedUser);
                return true;
            }
        }
        return false;
    }
}
