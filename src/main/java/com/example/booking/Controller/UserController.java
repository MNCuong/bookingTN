package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.RegisterFlightRequest;
import com.example.booking.DTO.Request.RegisterRequest;
import com.example.booking.DTO.Response.UserResponse;
import com.example.booking.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<UserResponse>> register(@ModelAttribute RegisterRequest registerRequest) {
        return ResponseConfig.success(userService.registerUser(registerRequest));

    }
    @PostMapping("/register-hotel")
    public ResponseEntity<ResponseDto<UserResponse>> registerHotel(@ModelAttribute RegisterRequest registerRequest) {
        return ResponseConfig.success(userService.registerHotel(registerRequest));

    }

    @PostMapping("/register-airline")
    public ResponseEntity<ResponseDto<UserResponse>> registerAirline(@ModelAttribute RegisterFlightRequest registerFlightRequest) {
        return ResponseConfig.success(userService.registerAirline(registerFlightRequest));
    }
}
