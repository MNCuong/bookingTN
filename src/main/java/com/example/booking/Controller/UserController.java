package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
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
    public ResponseEntity<ResponseDto<UserResponse>> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseConfig.success(userService.registerUser(registerRequest));

    }
    @PostMapping("/register-hotel")
    public ResponseEntity<ResponseDto<UserResponse>> registerHotel(@RequestBody RegisterRequest registerRequest) {
        return ResponseConfig.success(userService.registerHotel(registerRequest));

    }


}
