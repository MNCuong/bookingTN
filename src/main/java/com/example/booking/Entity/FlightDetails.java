package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flight_details")
public class FlightDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String iata;
    private String icao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codeshare_id")
    private CodeSharedFlight codeshared;
}
