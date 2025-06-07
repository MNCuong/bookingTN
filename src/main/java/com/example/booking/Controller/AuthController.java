package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.LoginRequest;
import com.example.booking.DTO.Request.RefreshRequest;
import com.example.booking.DTO.Response.AuthResponse;
import com.example.booking.Entity.User;
import com.example.booking.Entity.UserLoginHistory;
import com.example.booking.Notify.TelegramNotification;
import com.example.booking.Repository.UserLoginHistoryRepository;
import com.example.booking.Service.EmailService;
import com.example.booking.Service.Impl.UserDetailsServiceImpl;
import com.example.booking.Service.Impl.RedisService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final EmailService emailService;

//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<AuthResponse>> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        User user = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            log.info("✅ Xác thực thành công cho: " + request.getEmail());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userService.findUserByEmail(userDetails.getUsername());

            System.out.println("eole: "+userDetails.getAuthorities());
            String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
            log.info("token: "+token);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            String currentUserAgent = httpRequest.getHeader("User-Agent");

            boolean isNewDevice = userLoginHistoryRepository
                    .findByUsername(userDetails.getUsername())
                    .stream()
                    .noneMatch(history -> history.getUserAgent().equals(currentUserAgent));

            if (isNewDevice) {
                if(user.getRoles().equals("ADMIN")) {
                    telegramNotification.sendTelegramNotification(
                            "Admin " + user.getEmail() + " vừa đăng nhập bằng thiết bị lạ lúc " + LocalDateTime.now()
                    );
                }else{
                    emailService.sendSimpleMessage(user.getEmail(),"Phát hiện đăng nhập từ thiết bị lạ","Phát hiện tài khoản " + user.getEmail() + " vừa đăng nhập bằng thiết bị lạ lúc " + LocalDateTime.now());
                }
            }

            UserLoginHistory history = new UserLoginHistory();
            history.setUsername(userDetails.getUsername());
            history.setLoginTime(LocalDateTime.now());
            history.setUserAgent(currentUserAgent);
            userLoginHistoryRepository.save(history);

            return ResponseConfig.success(new AuthResponse(refreshToken, token, user.getLock_time(), user.getCount()));
        } catch (BadCredentialsException e) {
            log.error("❌ Sai tài khoản hoặc mật khẩu: " + e.getMessage());

            user = userService.findUserByEmail(request.getEmail());
            if (user != null) {

                int remaining = Math.max(0, 5 - user.getCount());
                String message = user.getCount() >= 5
                        ? "Tài khoản đã bị khóa do nhập sai quá nhiều lần. Vui lòng thử lại sau 15 phút."
                        : "Bạn đã nhập sai mật khẩu " + user.getCount() + " lần. Bạn còn " + remaining + " lần thử.";
                return ResponseEntity.status(HttpStatus.LOCKED).body(
                        new ResponseDto<>("423", message,
                                new AuthResponse(null, null, user.getLock_time(), user.getCount()))
                );
            }

            return ResponseConfig.error("Sai tài khoản hoặc mật khẩu");
        } catch (Exception e) {
            log.error("❌ Xác thực thất bại: " + e.getMessage());
            user = userService.findUserByEmail(request.getEmail());
            if (user != null) {
                return ResponseEntity.status(HttpStatus.LOCKED).body(
                        new ResponseDto<>("423",  e.getMessage(),
                                new AuthResponse(null, null, user.getLock_time(), user.getCount())));
            }
            return ResponseConfig.error(" Xác thực thất bại:" + e.getMessage());
        }
    }


    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
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
        String newToken = jwtUtil.generateToken(email, userDetails.getAuthorities());
        return ResponseConfig.success(new AuthResponse( request.getRefreshToken(),newToken));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String userId, @RequestParam String token) {
        log.info("idusser=======================" + userId + "abc");

        if (verificationService.isTokenValid(userId, token)) {
            userService.activateUser(userId);  // Kích hoạt tài khoản
            verificationService.deleteVerificationToken(userId);
            return ResponseEntity.ok("Account verified successfully!");
        } else {
            log.info("idusser=======================" + userId + "abc");
            verificationService.deleteVerificationToken(userId);
            Long idUser = Long.parseLong(userId);
            userService.deleteById(idUser);  // Xóa tài khoản nếu token không hợp lệ
            return ResponseEntity.badRequest().body("Invalid or expired token! Registration failed.");
        }
    }


}
