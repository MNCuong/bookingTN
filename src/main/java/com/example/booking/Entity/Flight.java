package com.example.booking.Entity;

import lombok.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime flightDate;
    private String flightStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departure_id")
    private AirportInfo departure;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arrival_id")
    private AirportInfo arrival;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Airlines_id")
    private Airlines Airlines;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_details_id")
    private FlightDetails flightDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    private BigDecimal priceEconomy;
    private BigDecimal priceBusiness;
}



