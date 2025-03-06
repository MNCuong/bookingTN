package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.UserProfileFlightRequest;
import com.example.booking.DTO.Response.UserProfileFlightResponse;
import com.example.booking.Service.UserProfileFlightService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/user-profile-flight")
public class UserProfileFlightController {
    private final UserProfileFlightService userProfileFlightService;


    @PostMapping("/add-user-profile-flight")
    public ResponseEntity<ResponseDto<UserProfileFlightResponse>> addUserProfileFlight(UserProfileFlightRequest request) {
        return ResponseConfig.success(userProfileFlightService.save(request));
    }
}
