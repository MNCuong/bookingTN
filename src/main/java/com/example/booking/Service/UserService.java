package com.example.booking.Service;

import com.example.booking.DTO.Request.RegisterRequest;
import com.example.booking.DTO.Response.UserResponse;
import com.example.booking.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserByEmail(String Email);
    UserResponse registerUser(RegisterRequest request);
    boolean verifyUser(String token);
    UserResponse registerHotel(RegisterRequest registerRequest);
    void deleteById(Long id);
    List<String> getUnverifiedUserIds();
    void activateUser(String userId);

    Optional<User> findUserById(long userId);
}
