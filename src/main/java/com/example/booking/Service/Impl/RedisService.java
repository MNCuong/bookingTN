package com.example.booking.Service.Impl;

import com.example.booking.Entity.Booking;
import com.example.booking.Entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class RedisService {
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

    public void deleteRedis(String key) {
        redisTemplate.delete(key);
    }

    public void saveBookingAndUser(String bookingId, String userId) {
        String keyBooking = "idBooking:" + bookingId;
        String keyUser = "idUser:" + userId;
        redisTemplate.opsForValue().set(keyBooking, bookingId, Duration.ofMinutes(30));
        redisTemplate.opsForValue().set(keyUser, userId, Duration.ofMinutes(30));
    }

    public Map<String, String> getIdBookingAndIdUser(Booking booking, User user) {
        String keyBooking = "idBooking:" + booking.getId();
        String keyUser = "idUser:" + user.getId();
        String idBooking = redisTemplate.opsForValue().get(keyBooking);
        String idUser = redisTemplate.opsForValue().get(keyUser);
        Map<String, String> result = new HashMap<>();
        result.put("idBooking", idBooking);
        result.put("idUser", idUser);
        return result;
    }

}
