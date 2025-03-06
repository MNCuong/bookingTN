package com.example.booking.Service;

import com.example.booking.DTO.Request.UserProfileFlightRequest;
import com.example.booking.DTO.Response.UserProfileFlightResponse;

public interface UserProfileFlightService {
    UserProfileFlightResponse save(UserProfileFlightRequest request);
}
