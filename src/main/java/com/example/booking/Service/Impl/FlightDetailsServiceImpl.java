package com.example.booking.Service.Impl;

import com.example.booking.DTO.Request.FlightRequestPackage.FlightDetailsRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.CodeSharedFlight;
import com.example.booking.Entity.FlightDetails;
import com.example.booking.Repository.FlightDetailsRepository;
import com.example.booking.Service.CodeSharedFlightService;
import com.example.booking.Service.FlightDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FlightDetailsServiceImpl implements FlightDetailsService {
    private final FlightDetailsRepository flightDetailsRepository;
    private final CodeSharedFlightService codeSharedFlightService;

    @Override
    public FlightDetails findById(Long id) {
        return flightDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public FlightDetails saveRequest(FlightDetailsRequest request) {
        CodeSharedFlight codeSharedFlight = codeSharedFlightService.findById(request.getCodesharedId());
        return flightDetailsRepository.save(FlightDetails.builder()
                .codeshared(codeSharedFlight)
                .iata(request.getIata())
                .icao(request.getIcao())
                .number(request.getNumber())
                .build());
    }

    @Override
    public FlightDetails save(FlightDetails flightDetails) {
        return flightDetailsRepository.save(flightDetails);
    }

    @Override
    public Optional<FlightDetails> findByNumberOrIataOrIcaoAndAirline(String number, String iata, String icao, Airlines airline) {
        return flightDetailsRepository.findFirstByNumberOrIataOrIcaoAndAirline(number, iata, icao, airline);
    }
}
