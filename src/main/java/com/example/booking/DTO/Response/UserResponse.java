package com.example.booking.DTO.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
public class UserResponse {
    private String email;
    private String full_name;
    private String phone_number;
    private String role;
    private LocalDateTime created_at;
}
