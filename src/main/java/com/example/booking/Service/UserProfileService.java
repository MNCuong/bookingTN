package com.example.booking.Service;

import com.example.booking.DTO.Request.UserProfileRequest;
import com.example.booking.DTO.Response.UserProfileResponse;
import com.example.booking.Entity.UserProfile;
import jakarta.servlet.http.HttpServletRequest;

public interface UserProfileService {
    UserProfileResponse saveUserProfile(UserProfileRequest request, HttpServletRequest httpServletRequest);
    void save(UserProfile userProfile);
    UserProfileResponse userDetail(HttpServletRequest httpServletRequest);
}
