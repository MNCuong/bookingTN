package com.example.booking.Repository;

import com.example.booking.Entity.AirportInfo;
import com.example.booking.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByArrivalAndDeparture(AirportInfo arrival, AirportInfo departure);
}
