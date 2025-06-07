package com.example.booking.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthResponse {
    public AuthResponse(String refresh_token, String token, LocalDateTime lock_time, int count) {
        this.refresh_token = refresh_token;
        this.lock_time = lock_time;
        this.count = count;
        this.token = token;
    }

    public AuthResponse(String refresh_token, String token) {
        this.refresh_token = refresh_token;
        this.token = token;
    }

    private String token;
    private String refresh_token;
    private LocalDateTime lock_time;
    private int count;
}
