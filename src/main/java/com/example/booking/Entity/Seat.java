package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    private String seatClass;
    private String status;

}
