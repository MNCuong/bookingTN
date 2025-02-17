package com.example.booking.Entity;

import com.example.booking.Enum.RoomTypeEnums;
import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Hotel hotel;
    private Double price;
    private String type;
    private int capacity;
    private boolean availability;
}

