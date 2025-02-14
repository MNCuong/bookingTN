package com.example.booking.Service;

import com.example.booking.DTO.Request.RegisterRequest;
import com.example.booking.DTO.Response.UserResponse;
import com.example.booking.Entity.User;

public interface UserService {
    User findUserByEmail(String Email);
    UserResponse registerUser(RegisterRequest request);
    boolean verifyUser(String token);
}
