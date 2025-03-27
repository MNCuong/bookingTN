package com.example.booking.Mapper;

import com.example.booking.DTO.Response.UserProfileResponse;
import com.example.booking.Entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

public interface UserProfileMapper {
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
