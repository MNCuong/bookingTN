package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_profile_flight")
public class UserProfileFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName; // Họ tên đầy đủ
    private String email;
    private String phone;
    private LocalDate dateOfBirth; // Ngày sinh
    private String gender; // Giới tính
    private String nationality; // Quốc tịch
    private String nationalityCode; // Mã quốc tịch
}

