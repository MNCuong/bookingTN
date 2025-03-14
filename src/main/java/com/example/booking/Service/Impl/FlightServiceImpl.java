package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Repository.FlightRepository;
import com.example.booking.Service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    @Value("${aviationstack.url}")
    private String API_URL;
    @Value("${aviationstack.key}")
    private String API_KEY;
    @Autowired
    private MessageCommon messageCommon;
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private FlightRepository flightRepository;


    @Override
    public String searchFlights(String depIata, String arrIata) {
        RestTemplate restTemplate = new RestTemplate();
        String url;

        url = API_URL + "?access_key=" + API_KEY
                + "&dep_iata=" + depIata
                + "&arr_iata=" + arrIata ;
        log.info("url:{}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
    @Override
    public Object convertToJson(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public int getAvailableSeats(String flightCode) {
//        int bookedSeats = flightRepository.countByFlightCodeAndStatus(flightCode, "CONFIRMED");
//        return MAX_SEATS - bookedSeats;
//    }
}
