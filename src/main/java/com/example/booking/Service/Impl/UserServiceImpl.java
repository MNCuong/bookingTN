package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.FlightRequestPackage.RegisterFlightRequest;
import com.example.booking.DTO.Request.RegisterRequest;
import com.example.booking.DTO.Response.UserResponse;
import com.example.booking.Entity.User;
import com.example.booking.Exception.BookingException;
import com.example.booking.Repository.UserRepository;
import com.example.booking.Service.EmailService;
import com.example.booking.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageCommon messageCommon;
    private final EmailService emailService;
    private final RedisService verificationService;

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
                .fullName("CUS_" + registerRequest.getFull_name())
                .createdAt(LocalDateTime.now())
                .verified(false)
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
                .fullName("HOTEL_" + registerRequest.getFull_name())
                .createdAt(LocalDateTime.now())
                .verified(false)
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

    @Override
    public UserResponse registerAirline(RegisterFlightRequest registerFlightRequest) {
        if (userRepository.existsByEmail(registerFlightRequest.getEmail())) {
            throw new BookingException(ServiceMessageConstants.EMAIL_EXIST, messageCommon.getMessage(ServiceMessageConstants.EMAIL_EXIST));
        }
        if (userRepository.existsByPhone(registerFlightRequest.getPhone_number())) {
            throw new BookingException(ServiceMessageConstants.PHONE_EXIST, messageCommon.getMessage(ServiceMessageConstants.PHONE_EXIST));
        } if (userRepository.existsByFullName(registerFlightRequest.getFull_name())) {
            throw new BookingException(ServiceMessageConstants.FULLNAME_AIRLINE_EXIST, messageCommon.getMessage(ServiceMessageConstants.FULLNAME_AIRLINE_EXIST));
        }

        User savedUser = userRepository.save(User.builder()
                .phone(registerFlightRequest.getPhone_number())
                .email(registerFlightRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerFlightRequest.getPassword()))
                .fullName("AIRLINE_" + registerFlightRequest.getFull_name())
                .createdAt(LocalDateTime.now())
                .verified(false)
                .roles("ADMIN")
                .build());
        emailService.sendVerificationEmail(savedUser);

        return UserResponse.builder()
                .phone_number(registerFlightRequest.getPhone_number())
                .email(registerFlightRequest.getEmail())
                .full_name(registerFlightRequest.getFull_name())
                .created_at(savedUser.getCreatedAt())
                .role(savedUser.getRoles())
                .build();
    }

    public boolean verifyUser(String token) {
        log.info("token-----------------------: " + token);

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

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<String> getUnverifiedUserIds() {
        return userRepository.findByVerifiedFalse()
                .stream()
                .map(user -> String.valueOf(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void activateUser(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setVerified(true);
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(long userId) {
        return userRepository.findById(userId);
    }

    @Scheduled(fixedRate = 60000)  // Chạy mỗi 1 phút
    public void deleteUnverifiedUsers() {
        List<String> unverifiedUserIds = getUnverifiedUserIds();
        for (String userId : unverifiedUserIds) {
            if (!verificationService.isTokenValid(userId, "")) {
                Long idUser = Long.parseLong(userId);// Token hết hạn
                userRepository.deleteById(idUser);
            }
        }
    }
}
