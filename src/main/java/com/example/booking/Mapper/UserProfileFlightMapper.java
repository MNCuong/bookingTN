package com.example.booking.Mapper;

import com.example.booking.DTO.Response.UserProfileFlightResponse;
import com.example.booking.Entity.UserProfileFlight;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileFlightMapper {
    UserProfileFlightResponse toUserProfileFlightResponse(UserProfileFlight userProfileFlight);
}
