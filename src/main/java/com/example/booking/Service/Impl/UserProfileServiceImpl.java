package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.UserProfileRequest;
import com.example.booking.DTO.Response.UserProfileResponse;
import com.example.booking.Entity.User;
import com.example.booking.Entity.UserProfile;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.UserProfileMapper;
import com.example.booking.Repository.UserProfileRepository;
import com.example.booking.Service.UserProfileService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    @Lazy
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final MessageCommon messageCommon;
    private final UserProfileMapper userProfileMapper;


    @Override
    public UserProfileResponse saveUserProfile(UserProfileRequest request, HttpServletRequest httpServletRequest) {
        String email = extractEmailByToken(httpServletRequest);
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new BookingException(ServiceMessageConstants.USER_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.USER_NOT_FOUND));
        }
        UserProfile existingProfile = userProfileRepository.findByUser_Id(user.getId());

        if (existingProfile == null) {
            existingProfile = new UserProfile();
            existingProfile.setUser(user);
        }
        existingProfile.setAddress(request.getAddress());
        existingProfile.setDateOfBirth(request.getDateOfBirth());
        existingProfile.setGender(request.getGender());
        existingProfile.setAvatarUrl(request.getAvatarUrl());
        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(existingProfile));
    }


    @Override
    public void save(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfileResponse userDetail(HttpServletRequest httpServletRequest) {
        String email = extractEmailByToken(httpServletRequest);
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new BookingException(ServiceMessageConstants.USER_NOT_FOUND,
                    messageCommon.getMessage(ServiceMessageConstants.USER_NOT_FOUND));
        }
        UserProfile userProfile = userProfileRepository.findByUser_Id(user.getId());
        if (userProfile == null) {
            throw new BookingException(ServiceMessageConstants.USER_PROFILE_NOT_FOUND,
                    messageCommon.getMessage(ServiceMessageConstants.USER_PROFILE_NOT_FOUND));
        }
        return userProfileMapper.toUserProfileResponse(userProfile);
    }



    public String extractEmailByToken(HttpServletRequest httpServletRequest) {
        String token = JwtUtil.getTokenFromRequest(httpServletRequest);
        String email = jwtUtil.extractUsername(token);
        return email;
    }
}
