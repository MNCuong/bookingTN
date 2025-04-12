package com.example.booking.Repository;

import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.AirportInfo;
import com.example.booking.Entity.Flight;
import com.example.booking.Enum.FlightStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByArrivalAndDeparture(AirportInfo arrival, AirportInfo departure);

    List<Flight> findByFlightStatus(FlightStateEnum flightStatus);

    List<Flight> findByAirlines(Airlines airlines);

    List<Flight> findAllByIsDeleted(Boolean isDeleted);

    List<Flight> findByAirlinesAndIsDeleted(Airlines airlines, Boolean isDeleted);

    List<Flight> findByArrivalAndDepartureAndFlightDateBetween(AirportInfo arrival, AirportInfo departure, LocalDateTime flightDateAfter, LocalDateTime flightDateBefore);

//    boolean findByAircraft(Aircraft aircraft);

    List<Flight> findByAircraftAndFlightStatusIn(Aircraft aircraft, Collection<FlightStateEnum> flightStatuses);

    List<Flight> findByAircraft(Aircraft aircraft);
}
