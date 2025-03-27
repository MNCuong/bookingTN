package com.example.booking.Mapper.Impl;

import com.example.booking.DTO.Response.UserProfileResponse;
import com.example.booking.Entity.UserProfile;
import com.example.booking.Mapper.UserProfileMapper;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapperImpl implements UserProfileMapper {
    @Override
    public UserProfileResponse toUserProfileResponse(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        UserProfileResponse userProfileResponse = UserProfileResponse.builder()
                .user(userProfile.getUser())
                .address(userProfile.getAddress())
                .gender(userProfile.getGender())
                .dateOfBirth(userProfile.getDateOfBirth())
                .dateOfBirth(userProfile.getDateOfBirth())
                .build();

        return null;
    }
}
