package com.example.booking.Service.Impl;

import com.example.booking.DTO.Request.UserProfileFlightRequest;
import com.example.booking.DTO.Response.UserProfileFlightResponse;
import com.example.booking.Entity.UserProfileFlight;
import com.example.booking.Mapper.UserProfileFlightMapper;
import com.example.booking.Repository.UserProfileFlightRepository;
import com.example.booking.Service.UserProfileFlightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserProfileFlightServiceImpl implements UserProfileFlightService {
    private final UserProfileFlightRepository userProfileFlightRepository;
    private final UserProfileFlightMapper userProfileFlightMapper;


    @Override
    public UserProfileFlightResponse save(UserProfileFlightRequest request) {
        UserProfileFlight userProfileFlight = new UserProfileFlight();

        return userProfileFlightMapper.toUserProfileFlightResponse(userProfileFlightRepository.save(userProfileFlight));
    }
}
