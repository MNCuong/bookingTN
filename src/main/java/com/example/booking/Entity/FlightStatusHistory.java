package com.example.booking.Entity;

import com.example.booking.Enum.FlightStateEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class FlightStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Enumerated(EnumType.STRING)
    private FlightStateEnum oldStatus;

    @Enumerated(EnumType.STRING)
    private FlightStateEnum newStatus;

    private LocalDateTime changedAt;


}
