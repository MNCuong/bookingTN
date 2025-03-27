package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.FlightRequestPackage.MinPriceRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.SearchFlightLocationRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.SearchFlightRequest;
import com.example.booking.Exception.BookingException;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Entity.*;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.FlightMapper;
import com.example.booking.Repository.FlightRepository;
import com.example.booking.Service.FlightService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.booking.Service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    //    @Value("${aviationstack.url}")
//    private String API_URL;
//    @Value("${aviationstack.key}")
//    private String API_KEY;
//    @Autowired
    private final MessageCommon messageCommon;
    //    private final RestTemplate restTemplate = new RestTemplate();
//    @Autowired
    private final FlightRepository flightRepository;
    private final AircraftService aircraftService;
    private final AirlinesService airlinesService;
    private final FlightDetailsService flightDetailsService;
    private final CodeSharedFlightService codeSharedFlightService;
    private final AirPortInfoService airPortInfoService;
    private final FlightMapper flightMapper;


    //    @Override
//    public String searchFlights(String depIata, String arrIata) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url;
//
//        url = API_URL + "?access_key=" + API_KEY
//                + "&dep_iata=" + depIata
//                + "&arr_iata=" + arrIata ;
//        log.info("url:{}", url);
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        return response.getBody();
//    }
//    @Override
//    public Object convertToJson(String jsonString) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(jsonString, Map.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//

    @Override
    public FlightResponse createFlight(FlightRequest flightRequest) {
        if (flightRequest == null) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
        if (flightRequest.getFlight_details_id() == null || flightRequest.getFlight_details_id() == 0) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
        if (flightRequest.getAircraft_id() == null || flightRequest.getAircraft_id() == 0) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
        if (flightRequest.getArrival_id() == null || flightRequest.getArrival_id() == 0) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
        if (flightRequest.getAirline_id() == null || flightRequest.getAirline_id() == 0) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
        if (flightRequest.getDeparture_id() == null || flightRequest.getDeparture_id() == 0) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
        if (flightRequest.getFight_date() == null || flightRequest.getFlight_status() == null || flightRequest.getFlight_status().isEmpty()) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }

        Airlines airlines = airlinesService.findById(flightRequest.getAirline_id());
        Aircraft aircraft = aircraftService.findById(flightRequest.getAircraft_id());
        AirportInfo arrival = airPortInfoService.findById(flightRequest.getArrival_id());
        AirportInfo departure = airPortInfoService.findById(flightRequest.getDeparture_id());
        CodeSharedFlight codeSharedFlight = codeSharedFlightService.findById(flightRequest.getAircraft_id());
        FlightDetails flightDetails = flightDetailsService.findById(flightRequest.getAircraft_id());
        if (airlines == null || arrival == null || departure == null || codeSharedFlight == null || flightDetails == null || aircraft == null) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }


        Flight flight = Flight.builder()
                .flightDetails(flightDetails)
                .flightDate(flightRequest.getFight_date())
                .aircraft(aircraft)
                .flightStatus(flightRequest.getFlight_status())
                .arrival(arrival)
                .departure(departure)
                .Airlines(airlines)
                .build();
        return flightMapper.toFlightResponse(flightRepository.save(flight));
    }

    @Override
    public List<FlightResponse> getAllFlights() {
        return flightMapper.toFlightResponseList(flightRepository.findAll());
    }

    @Override
    public List<FlightResponse> searchFlight(String arrival, String departure) {
        if (arrival == null || arrival.isEmpty() || departure == null || departure.isEmpty()) {
            throw new BookingException(ServiceMessageConstants.CHOOSE_DESTINATION_AND_PLACE, messageCommon.getMessage(ServiceMessageConstants.CHOOSE_DESTINATION_AND_PLACE));
        }
        AirportInfo arrival_airport = airPortInfoService.findByIata(arrival);
        AirportInfo departure_airport = airPortInfoService.findByIata(arrival);
        List<Flight> list = flightRepository.findByArrivalAndDeparture(arrival_airport, departure_airport);
        return flightMapper.toFlightResponseList(list);
    }

    @Override
    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id).orElse(null);
        return flightMapper.toFlightResponse(flight);
    }

//    @Override
//    public int getAvailableSeats(String flightCode) {
//        int bookedSeats = flightRepository.countByFlightCodeAndStatus(flightCode, "CONFIRMED");
//        return MAX_SEATS - bookedSeats;
//    }
}
