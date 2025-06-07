package com.example.booking.Entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightCode;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "departure_id")
    private AirportInfo departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_id")
    private AirportInfo arrivalAirport;


    private String status;

    private String statusReason;

    @ManyToOne
    private Aircraft aircraft;

    private Double priceEconomy;
    private Double priceBusiness;

    private String departureGate;
    private String arrivalGate;

    private LocalDateTime checkInDeadline;
    private LocalDateTime boardingTime;

    private String bookingReference;

    private Boolean isDeleted;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Integer seats;
    private Integer availableSeats;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airlines airline;

    @ManyToMany
    @JoinTable(
            name = "flight_crew",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "crew_id")
    )
    private List<CrewMember> crew;

    @OneToMany(mappedBy = "flight")
    private List<Ticket> tickets;
}

