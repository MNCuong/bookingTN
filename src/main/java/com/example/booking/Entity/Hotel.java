package com.example.booking.Entity;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String description;
    private String phone;
//    private List<String> imgs;
    private BigDecimal rating;
    private LocalDateTime createdAt = LocalDateTime.now();
}

