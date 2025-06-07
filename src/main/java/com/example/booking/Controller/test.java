package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.LoginRequest;
import com.example.booking.DTO.Response.AuthResponse;
import com.example.booking.Entity.User;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class test {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    @PostMapping("")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        User user=userService.findUserByEmail(request.getEmail());
        List<GrantedAuthority> authorities = Arrays.stream(user.getRoles().split(","))
                .map(String::trim) // loại bỏ khoảng trắng nếu có
                .map(SimpleGrantedAuthority::new) // tạo GrantedAuthority từ role string
                .collect(Collectors.toList());
        String token = jwtUtil.generateToken(user.getEmail(), authorities);
return ResponseConfig.success(token);
    }
}
