package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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
    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airlines airline;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codeshare_id",nullable = true, unique = false)
    private CodeSharedFlight codeshared;
}
