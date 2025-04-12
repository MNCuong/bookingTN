package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.Enum.AircraftStatusEnum;
import com.example.booking.Enum.FlightStateEnum;
import com.example.booking.Exception.BookingException;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Entity.*;
import com.example.booking.Mapper.FlightMapper;
import com.example.booking.Repository.FlightRepository;
import com.example.booking.Service.FlightService;
import com.example.booking.Utils.JwtUtil;
import com.example.booking.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private final JwtUtil jwtUtil;
    private final UserService userService;


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
        validateFlight(flightRequest);
        if (flightRequest.getFlight_date() == null || flightRequest.getFlight_date_land() == null) {
            throw new BookingException("Thời gian cất cánh và hạ cánh không được để trống.");
        }

        if (flightRequest.getFlight_date().isAfter(flightRequest.getFlight_date_land())) {
            throw new BookingException("Thời gian hạ cánh phải sau thời gian cất cánh!");
        }

        Airlines airlines = airlinesService.findById(flightRequest.getAirline_id());
        Aircraft aircraft = aircraftService.findById(flightRequest.getAircraft_id());
        AirportInfo arrival = airPortInfoService.findById(flightRequest.getArrival_id());
        AirportInfo departure = airPortInfoService.findById(flightRequest.getDeparture_id());
        CodeSharedFlight csf = codeSharedFlightService.findById(flightRequest.getFlight_details_request().getCodesharedId());
        if (airlines == null || arrival == null || departure == null || aircraft == null) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
        if (flightRequest.getFlight_date().isAfter(flightRequest.getFlight_date_land())) {
            throw new BookingException("Thời gian hạ cánh phải sau thời gian cất cánh!");
        }
        if (flightRequest.getArrival_id().equals(flightRequest.getDeparture_id())) {
            throw new BookingException("Sân bay đi và đến không được trùng nhau!");
        }

        AircraftStatusEnum status = aircraft.getStatus();
        if (Set.of(
                AircraftStatusEnum.UNDER_MAINTENANCE,
                AircraftStatusEnum.REPAIRING,
                AircraftStatusEnum.AOG,
                AircraftStatusEnum.OUT_OF_SERVICE,
                AircraftStatusEnum.RETIRED,
                AircraftStatusEnum.SCRAPPED
        ).contains(status)) {
            throw new BookingException("Máy bay hiện không đủ điều kiện để hoạt động: " + status);
        }
        List<Flight> activeFlights = flightRepository.findByAircraftAndFlightStatusIn(
                aircraft,
                List.of(
                        FlightStateEnum.SCHEDULED,
                        FlightStateEnum.BOARDING,
                        FlightStateEnum.IN_FLIGHT,
                        FlightStateEnum.ON_TIME
                )
        );
        for (Flight f : activeFlights) { boolean overlap =
                    f.getFlightDateLand() != null &&
                            f.getFlightDate() != null &&
                            flightRequest.getFlight_date().isBefore(f.getFlightDateLand()) &&
                            flightRequest.getFlight_date_land().isAfter(f.getFlightDate());

            if (overlap) {
                throw new BookingException("Máy bay đang phục vụ chuyến bay khác trong khung giờ này");
            }
        }

        FlightDetails flightDetailsSave = null;

        if (flightRequest.getFlight_details_request() != null) {
            String number = flightRequest.getFlight_details_request().getNumber();
            String iata = flightRequest.getFlight_details_request().getIata();
            String icao = flightRequest.getFlight_details_request().getIcao();

            flightDetailsSave = flightDetailsService
                    .findByNumberOrIataOrIcaoAndAirline(number, iata, icao, airlines)
                    .orElseGet(() -> {
                        FlightDetails newFlightDetails = FlightDetails.builder()
                                .iata(iata)
                                .icao(icao)
                                .number(number)
                                .codeshared(csf)
                                .airline(airlines)
                                .build();
                        return flightDetailsService.save(newFlightDetails);
                    });
        }
        Flight flight = Flight.builder()
                .flightDetails(flightDetailsSave)
                .flightDate(flightRequest.getFlight_date())
                .aircraft(aircraft)
                .flightStatus(flightRequest.getFlight_status())
                .arrival(arrival)
                .departure(departure)
                .airlines(airlines)
                .priceEconomy(flightRequest.getPriceEconomy())
                .priceBusiness(flightRequest.getPriceBusiness())
                .isDeleted(false)
                .build();
        return flightMapper.toFlightResponse(flightRepository.save(flight));
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        if (id == null || id == 0) {
            throw new BookingException(ServiceMessageConstants.FLIGHT_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.FLIGHT_NOT_FOUND));
        }
        validateFlight(flightRequest);
        Optional<Flight> flight = flightRepository.findById(id);
        return flightMapper.toFlightResponse(flightRepository.save(Flight.builder()
                .flightDetails(flight.get().getFlightDetails())
                .flightDate(flight.get().getFlightDate())
                .aircraft(flight.get().getAircraft())
                .flightStatus(flight.get().getFlightStatus())
                .arrival(flight.get().getArrival())
                .departure(flight.get().getDeparture())
                .airlines(flight.get().getAirlines())
                .priceEconomy(flightRequest.getPriceEconomy())
                .priceBusiness(flightRequest.getPriceBusiness())
                .isDeleted(flightRequest.getIsDeleted())
                .build()));
    }

    @Override
    public String deleteFlight(Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        if (flight.isEmpty()) {
            throw new BookingException(ServiceMessageConstants.FLIGHT_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.FLIGHT_NOT_FOUND));
        }
        if (flight.get().getIsDeleted()) {
            throw new BookingException(ServiceMessageConstants.FLIGHT_NOT_EXIST, messageCommon.getMessage(ServiceMessageConstants.FLIGHT_NOT_EXIST));
        }
        flight.get().setIsDeleted(true);
        flightRepository.save(flight.get());
        return "Xóa chuyến bay thành công";
    }

    private void validateFlight(FlightRequest flightRequest) {
        if (flightRequest == null) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
        if (flightRequest.getFlight_details_request() == null || flightRequest.getFlight_details_request().getCodesharedId() == 0) {
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
        if (flightRequest.getFlight_date() == null || flightRequest.getFlight_status() == null) {
            throw new BookingException(ServiceMessageConstants.CREATE_FLIGHT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_FLIGHT_FAILD));
        }
    }

    @Override
    public List<FlightResponse> getAllFlights() {
        return flightMapper.toFlightResponseList(flightRepository.findAllByIsDeleted(false));
    }

    @Override
    public List<FlightResponse> getAllFlightsByAirLine(HttpServletRequest request) {
        Airlines airlines = ServiceCommon.extractAirline(request, jwtUtil, userService, airlinesService);
        return flightMapper.toFlightResponseList(flightRepository.findByAirlines(airlines));
    }

    @Override
    public List<FlightResponse> searchFlight(LocalDate date, String arrival, String departure) {
        if (arrival == null || arrival.isEmpty() || departure == null || departure.isEmpty()) {
            throw new BookingException(ServiceMessageConstants.CHOOSE_DESTINATION_AND_PLACE, messageCommon.getMessage(ServiceMessageConstants.CHOOSE_DESTINATION_AND_PLACE));
        }
        AirportInfo arrival_airport = airPortInfoService.findByIata(arrival);
        AirportInfo departure_airport = airPortInfoService.findByIata(departure);
        if (arrival_airport == null || departure_airport == null) {
            throw new BookingException(ServiceMessageConstants.AIRPORT_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.AIRPORT_NOT_FOUND));

        }
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<Flight> list = flightRepository.findByArrivalAndDepartureAndFlightDateBetween(
                arrival_airport, departure_airport, startOfDay, endOfDay);
        return flightMapper.toFlightResponseList(list);
    }

    @Override
    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id).orElse(null);
        return flightMapper.toFlightResponse(flight);
    }

    @Override
    public List<FlightResponse> getFlightByStatus(String status) {
        return List.of();
    }

    public List<FlightResponse> getFlightByStatus(FlightStateEnum status) {
        if (status == null) {
            return flightMapper.toFlightResponseList(flightRepository.findAll());
        }
        return flightMapper.toFlightResponseList(flightRepository.findByFlightStatus(status));
    }

    @Override
    public String updateStatusFlight(Long id, FlightStateEnum status) {
        if (id == null || id == 0) {
            throw new BookingException(ServiceMessageConstants.FLIGHT_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.FLIGHT_NOT_FOUND));
        }
        Flight flight = flightRepository.findById(id).orElse(null);
        assert flight != null;
        if (!flight.getFlightStatus().canTransitionTo(status)) {
            throw new BookingException(ServiceMessageConstants.NOT_UPDATE_STATE_FLIGHT, messageCommon.getMessage(ServiceMessageConstants.NOT_UPDATE_STATE_FLIGHT));

        }
        flight.setFlightStatus(status);
        flightRepository.save(flight);
        return "update flight state success";

    }


    @Override
    public int getSeat(Long id) {
        Flight flight = flightRepository.findById(id).orElse(null);
        assert flight != null;

        return flight.getAircraft().getType().getSeatCapacity();
    }

}
