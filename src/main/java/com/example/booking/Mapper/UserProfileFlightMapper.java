package com.example.booking.Mapper;

import com.example.booking.DTO.Response.UserProfileFlightResponse;
import com.example.booking.Entity.UserProfileFlight;

public interface UserProfileFlightMapper {
    UserProfileFlightResponse toUserProfileFlightResponse(UserProfileFlight userProfileFlight);
}
