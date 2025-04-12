package com.example.booking.Common;


import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.User;
import com.example.booking.Service.AirlinesService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class ServiceCommon {

    public String generateBookingId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
    public static Airlines extractAirline(HttpServletRequest request, JwtUtil jwtUtil, UserService userService, AirlinesService airlinesService) {
        String tokenS = JwtUtil.getTokenFromRequest(request);
        String email = jwtUtil.extractUsername(tokenS);
        User user = userService.findUserByEmail(email);
        String nameAirline = user.getFullName().substring(user.getFullName().indexOf("_") + 1);
        return airlinesService.findByName(nameAirline);
    }

}
