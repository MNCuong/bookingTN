package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.LoginRequest;
import com.example.booking.DTO.Request.RefreshRequest;
import com.example.booking.DTO.Response.AuthResponse;
import com.example.booking.Entity.UserLoginHistory;
import com.example.booking.Notify.TelegramNotification;
import com.example.booking.Repository.UserLoginHistoryRepository;
import com.example.booking.Service.Impl.UserDetailsServiceImpl;
import com.example.booking.Service.Impl.RedisService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

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
    private final UserLoginHistoryRepository userLoginHistoryRepository;
    private final TelegramNotification telegramNotification;

//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }

    @PostMapping(value="/login")
    public ResponseEntity<ResponseDto<AuthResponse>> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            log.info("✅ Xác thực thành công cho: " + request.getEmail());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            boolean isUser = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("USER"));
            log.info("authorities-----------------------"+ userDetails.getAuthorities());
            String token = jwtUtil.generateToken(userDetails.getUsername(),userDetails.getAuthorities());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
            String ip = getClientIp(httpRequest);
            String location = getLocationFromIp(ip);
            if (isUser) {


                UserLoginHistory history = new UserLoginHistory();
                history.setUsername(userDetails.getUsername());
                history.setLoginTime(LocalDateTime.now());
                history.setLoginIp(ip);
                history.setLoginLocation(location);
                userLoginHistoryRepository.save(history);
            }else{
                telegramNotification.sendTelegramNotification("Có người vừa đăng nhập trang quản trị với tài khoản: " + request.getEmail() + " tại vị: " + location + " với IP thiết bị: " + ip);

            }
            return ResponseConfig.success(new AuthResponse(token, refreshToken));
        } catch (BadCredentialsException e) {
            log.error("❌ Sai tài khoản hoặc mật khẩu: " + e.getMessage());
            return ResponseConfig.error("Sai tài khoản hoặc mật khẩu");
        } catch (Exception e) {
            log.error("❌ Xác thực thất bại: " + e.getMessage());
            return ResponseConfig.error("Xác thực thất bại");
        }
    }


    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private String getLocationFromIp(String ip) {
        try {
            URL url = new URL("http://ip-api.com/json/" + ip + "?fields=city,country");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.toString());

            String city = root.path("city").asText("");
            String country = root.path("country").asText("");

            return city + ", " + country;
        } catch (Exception e) {
            log.error("Lỗi lấy địa điểm IP: " + e.getMessage());
            return "Unknown";
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
