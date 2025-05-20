package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.ChangePasswordRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.RegisterFlightRequest;
import com.example.booking.DTO.Request.RegisterRequest;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.DTO.Response.UserResponse;
import com.example.booking.Entity.User;
import com.example.booking.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<UserResponse>> register(@RequestBody  RegisterRequest registerRequest) {
        return ResponseConfig.success(userService.registerUser(registerRequest));

    }

    @PostMapping("/register-hotel")
    public ResponseEntity<ResponseDto<UserResponse>> registerHotel(@RequestBody  RegisterRequest registerRequest) {
        return ResponseConfig.success(userService.registerHotel(registerRequest));

    }

    @PostMapping("/register-airline")
    public ResponseEntity<ResponseDto<UserResponse>> registerAirline(@RequestBody  RegisterFlightRequest registerFlightRequest) {
        return ResponseConfig.success(userService.registerAirline(registerFlightRequest));
    }

    @GetMapping("/info-by-email")
    public ResponseEntity<ResponseDto<UserResponse>> getInfoByEmail(@RequestParam String email) {
        return ResponseConfig.success(userService.getUserInfoByEmail(email));
    }
    @GetMapping("/user-profile")
    public ResponseEntity<ResponseDto<User>> getUserProfile(HttpServletRequest request) {
        return ResponseConfig.success(userService.getUserProfile(request));
    }
    @GetMapping("/list-user")
    public ResponseEntity<ResponseDto<Page<User>>> getAllUser(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size,
                                                              @RequestParam String search) {
        Page<User> userPage = userService.getAllUser(page, size, search);

        return ResponseConfig.success(userPage);
    }
    @PutMapping("/change-password")
    public ResponseEntity<ResponseDto<User>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest request) {
        return ResponseConfig.success(userService.changePass(changePasswordRequest,request));

    }
}
