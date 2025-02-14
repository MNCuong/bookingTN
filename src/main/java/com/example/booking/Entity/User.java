package com.example.booking.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Data
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String passwordHash;
    private LocalDateTime createdAt = LocalDateTime.now();
}