package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flight_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    private String status;

    private String statusReason;

    private LocalDateTime updateAt;

}
