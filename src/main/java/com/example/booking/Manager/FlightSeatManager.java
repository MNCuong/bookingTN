package com.example.booking.Manager;
import com.example.booking.Enum.AircraftTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class FlightSeatManager {
    private final ConcurrentHashMap<String, Integer> bookedSeats = new ConcurrentHashMap<>();

    private String generateKey(String flightCode, LocalDate flightDate, LocalTime departureTime, LocalTime arrivalTime) {
        log.info("key:{}",flightCode + "_" + flightDate.toString());
        return flightCode + "_" + flightDate.toString()+"_"+departureTime.toString()+"_"+arrivalTime.toString();

    }

    public int getAvailableSeats(String flightCode, String aircraftModel, LocalDate flightDate, LocalTime departureTime, LocalTime arrivalTime) {
        String key = generateKey(flightCode, flightDate,departureTime,arrivalTime);
        int totalSeats = AircraftTypeEnums.getSeatsByModel(aircraftModel);
        int booked = bookedSeats.getOrDefault(key, 0);
        return totalSeats - booked;
    }

    public synchronized void bookSeats(String flightCode, LocalDate flightDate, LocalTime departureTime, LocalTime arrivalTime, int seats) {
        String key = generateKey(flightCode, flightDate,departureTime,arrivalTime);
        bookedSeats.put(key, bookedSeats.getOrDefault(key, 0) + seats);
    }
}
