package com.example.booking.Service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@AllArgsConstructor
@Service
public class VerificationService {
    private final StringRedisTemplate redisTemplate;

    // Lưu token vào Redis với thời gian hết hạn 1 phút
    public void saveVerificationToken(String userId, String token) {
        String key = "verify:" + userId;
        redisTemplate.opsForValue().set(key, token, Duration.ofMinutes(1));
    }

    //kiểm tra token có hợp lệ thông
    public boolean isTokenValid(String userId, String token) {
        String key = "verify:" + userId;
        String storedToken = redisTemplate.opsForValue().get(key);
        return storedToken != null && storedToken.equals(token);
    }

    //Xóa token sau khi xác thực thành công
    public void deleteVerificationToken(String userId) {
        String key = "verify:" + userId;
        redisTemplate.delete(key);
    }
}
