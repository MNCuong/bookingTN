package com.example.booking.Repository;

import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.FlightDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightDetailsRepository extends JpaRepository<FlightDetails, Long> {
    Optional<FlightDetails> findFirstByNumberOrIataOrIcaoAndAirline(String number, String iata, String icao, Airlines airline);
}
