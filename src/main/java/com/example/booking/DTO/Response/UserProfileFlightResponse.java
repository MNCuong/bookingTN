package com.example.booking.DTO.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserProfileFlightResponse {
    private String fullName; // Họ tên đầy đủ
    private String email;
    private String phone;
    private LocalDate dateOfBirth; // Ngày sinh
    private String gender; // Giới tính
    private String nationality; // Quốc tịch
//    private String nationalityCode;
}
