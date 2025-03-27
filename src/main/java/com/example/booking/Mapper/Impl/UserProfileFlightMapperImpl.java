package com.example.booking.Mapper.Impl;

import com.example.booking.DTO.Response.UserProfileFlightResponse;
import com.example.booking.Entity.UserProfileFlight;
import com.example.booking.Mapper.UserProfileFlightMapper;
import org.springframework.stereotype.Component;

@Component
public class UserProfileFlightMapperImpl implements UserProfileFlightMapper {
    @Override
    public UserProfileFlightResponse toUserProfileFlightResponse(UserProfileFlight userProfileFlight) {
        if (userProfileFlight == null) {
            return null;
        }
        return UserProfileFlightResponse.builder()
                .phone(userProfileFlight.getPhone())
                .email(userProfileFlight.getEmail())
                .gender(userProfileFlight.getGender())
                .dateOfBirth(userProfileFlight.getDateOfBirth())
                .fullName(userProfileFlight.getFullName())
                .nationality(userProfileFlight.getNationality())
                .build();
    }
}
