//package com.example.booking.Common;
//
//import com.example.booking.Utils.JwtUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@AllArgsConstructor
//@Component
//public class ServiceCommon {
//    private final JwtUtil jwtUtil;
//
//    public String extractUsername( HttpServletRequest request) {
//        String token= JwtUtil.getTokenFromRequest(request);
//        return jwtUtil.extractUsername(token);
//    }
//}
