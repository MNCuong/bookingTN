package com.example.booking.Repository;

import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.AirportInfo;
import com.example.booking.Entity.Flight;
import com.example.booking.Enum.FlightStateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f \n" +
            "WHERE f.departureAirport.code IN :departureCodes\n" +
            "AND f.arrivalAirport.code IN :arrivalCodes\n" +
            "AND f.departureTime >= :startTime\n")
    Page<Flight> searchFlightsMultipleAirports(
            @Param("departures") List<AirportInfo> departureAirports,
            @Param("arrivals") List<AirportInfo> arrivalAirports,
            @Param("startTime") LocalDateTime startTime,
            Pageable pageable
    );


    Page<Flight> findAllByIsDeleted(boolean isDeleted, Pageable pageable);


    Page<Flight> findByDepartureAirport_CityAndArrivalAirport_CityAndDepartureTimeAfter(String departureAirportCity, String arrivalAirportCity, LocalDateTime departureTimeAfter, Pageable pageable);
}
