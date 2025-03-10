package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.LoginRequest;
import com.example.booking.DTO.Request.RefreshRequest;
import com.example.booking.DTO.Response.AuthResponse;
import com.example.booking.Service.Impl.UserDetailsServiceImpl;
import com.example.booking.Service.Impl.RedisService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RedisService verificationService;
    private final UserService userService;

//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }

    @PostMapping(value="/login")
    public ResponseEntity<ResponseDto<AuthResponse>> login(@ModelAttribute LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            log.info("✅ Xác thực thành công cho: " + request.getEmail());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.info("authorities-----------------------"+ userDetails.getAuthorities());
            String token = jwtUtil.generateToken(userDetails.getUsername(),userDetails.getAuthorities());
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
    public ResponseEntity<ResponseDto<AuthResponse>> refresh(@ModelAttribute RefreshRequest request) {
        String email = jwtUtil.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String newToken = jwtUtil.generateToken(email,userDetails.getAuthorities());
        return ResponseConfig.success(new AuthResponse(newToken, request.getRefreshToken()));
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String userId, @RequestParam String token) {
        log.info("idusser======================="+ userId+"abc");

        if (verificationService.isTokenValid(userId, token)) {
            userService.activateUser(userId);  // Kích hoạt tài khoản
            verificationService.deleteVerificationToken(userId);
            return ResponseEntity.ok("Account verified successfully!");
        } else {
            log.info("idusser======================="+ userId+"abc");
            verificationService.deleteVerificationToken(userId);
            Long idUser= Long.parseLong(userId);
            userService.deleteById(idUser);  // Xóa tài khoản nếu token không hợp lệ
            return ResponseEntity.badRequest().body("Invalid or expired token! Registration failed.");
        }
    }
}
