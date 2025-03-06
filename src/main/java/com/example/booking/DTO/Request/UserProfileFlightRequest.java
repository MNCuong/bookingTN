package com.example.booking.DTO.Request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserProfileFlightRequest {
    private String fullName; // Họ tên đầy đủ
    private String email;
    private String phone;
    private LocalDateTime dateOfBirth; // Ngày sinh
    private String gender; // Giới tính
    private String nationality; // Quốc tịch
    private String nationalityCode;
}
