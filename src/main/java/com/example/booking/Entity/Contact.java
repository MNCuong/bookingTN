package com.example.booking.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Contact {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Long userId;
        private String title;
        private String status; // "Pending", "Replied", "Closed"

        private LocalDateTime createdAt = LocalDateTime.now();

        // getters/setters
    }