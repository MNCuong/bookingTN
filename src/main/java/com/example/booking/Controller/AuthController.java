package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.LoginRequest;
import com.example.booking.DTO.Request.RefreshRequest;
import com.example.booking.DTO.Response.AuthResponse;
import com.example.booking.Service.Impl.UserDetailsServiceImpl;
import com.example.booking.Utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<AuthResponse>> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            log.info("✅ Xác thực thành công cho: " + request.getEmail());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            return ResponseConfig.success(new AuthResponse(token, refreshToken));
        } catch (BadCredentialsException e) {
            log.error("❌ Sai tài khoản hoặc mật khẩu: " + e.getMessage());
            return ResponseConfig.error("Sai tài khoản hoặc mật khẩu");
        } catch (Exception e) {
            log.error("❌ Xác thực thất bại: " + e.getMessage());
            return ResponseConfig.error("Xác thực thất bại");
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<AuthResponse>> refresh(@RequestBody RefreshRequest request) {
        String username = jwtUtil.extractUsername(request.getRefreshToken());
        String newToken = jwtUtil.generateToken(username);
        return ResponseConfig.success(new AuthResponse(newToken, request.getRefreshToken()));
    }
}
