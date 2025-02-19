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

    @GetMapping("/verify")
    public ResponseEntity<ResponseDto<String>> verifyUser(@RequestParam String token, HttpServletResponse response) {
        boolean verified = userService.verifyUser(token);
        if (verified) {
            return ResponseConfig.success("Account verified successfully!");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ResponseConfig.success("Invalid or expired token!");

        }
    }

}
