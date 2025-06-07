package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.ChangePasswordRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.RegisterFlightRequest;
import com.example.booking.DTO.Request.RegisterRequest;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.DTO.Response.UserResponse;
import com.example.booking.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserByEmail(String Email);

    UserResponse registerUser(RegisterRequest request);

    boolean verifyUser(String token);


    UserResponse registerAirline(RegisterFlightRequest registerFlightRequest);

    void deleteById(Long id);

    List<String> getUnverifiedUserIds();

    void activateUser(String userId);

    UserResponse getUserInfoByEmail(String email);

    User getUserProfile(HttpServletRequest request);

    List<User> getAllUser();

    Optional<User> findUserById(long userId);

    Page<User> getAllUser(int page, int size, String search);

    User changePass(ChangePasswordRequest changePasswordRequest, HttpServletRequest request);

    User forgotPassword(String email);

    User lock(String email);

    User resetPassword(String password, String email);

    void increaseFailedAttempts(String email);

    void resetFailedAttempts(String email);

//    boolean unlockWhenTimeExpired(User user);

}
