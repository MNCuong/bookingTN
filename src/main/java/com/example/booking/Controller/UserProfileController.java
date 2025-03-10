package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.RegisterRequest;
import com.example.booking.DTO.Request.UserProfileRequest;
import com.example.booking.DTO.Response.UserProfileResponse;
import com.example.booking.DTO.Response.UserResponse;
import com.example.booking.Service.UserProfileService;
import com.example.booking.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user-profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/save-user-profile")
    public ResponseEntity<ResponseDto<UserProfileResponse>> saveUserProfile(@ModelAttribute UserProfileRequest userProfileRequest, HttpServletRequest httpServletRequest) {
        return ResponseConfig.success(userProfileService.saveUserProfile(userProfileRequest,httpServletRequest));

    }
    @GetMapping("/user-detail")
    public ResponseEntity<ResponseDto<UserProfileResponse>> getUserProfile(HttpServletRequest httpServletRequest) {
        return ResponseConfig.success(userProfileService.userDetail(httpServletRequest));
    }
}
