package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Response.FlightResponsePackage.FlightsResponse;
import com.example.booking.Enum.FlightStateEnum;
import com.example.booking.Exception.BookingException;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.Entity.*;
import com.example.booking.Mapper.FlightMapper;
import com.example.booking.Repository.AirlinesRepository;
import com.example.booking.Repository.CrewMemberRepository;
import com.example.booking.Repository.FlightHistoryRepository;
import com.example.booking.Repository.FlightRepository;
import com.example.booking.Service.FlightService;
import com.example.booking.Utils.JwtUtil;
import com.example.booking.Service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private final AirPortInfoService airPortInfoService;
    private final FlightMapper flightMapper;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final FlightStatusHistoryService flightStatusHistoryService;
    private final CrewMemberRepository crewRepo;
    private final FlightHistoryRepository flightHistoryRepository;
    private final AirlinesRepository airlinesRepository;

    @Override
    public Page<FlightsResponse> getFlightHistory(Long flightId) {
        return null;
    }

    @Override
    public FlightsResponse createFlight(FlightRequest flightRequest) {
        log.info("aircraft, {}", flightRequest.getAircraftRegistration());
        log.info("aircraft, {}", flightRequest.getDepartureAirportId());
        log.info("aircraft, {}", flightRequest.getArrivalAirportId());

        try {
            Aircraft aircraft = aircraftService.getByRegistration(flightRequest.getAircraftRegistration());
            if (aircraft == null) {
                throw new BookingException(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED, messageCommon.getMessage(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED));
            }
            AirportInfo arrival = airPortInfoService.findById(flightRequest.getArrivalAirportId());
            if (arrival == null) {
                throw new BookingException(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED, messageCommon.getMessage(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED));
            }
            AirportInfo departure = airPortInfoService.findById(flightRequest.getDepartureAirportId());
            if (departure == null) {
                throw new BookingException(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED, messageCommon.getMessage(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED));
            }
            Airlines airlines=airlinesRepository.findById(flightRequest.getAirlineId()).get();
            if (airlines == null) {
                throw new BookingException(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED, "Không tìm thấy hãng bay");
            }
            if (flightRequest.getSeats() > aircraftService.getSeatByRegistration(aircraft.getRegistration())) {
                throw new BookingException("Error", "Ghế được yêu cầu (" + flightRequest.getSeats() + ") vượt quá chỗ ngồi có sẵn (" + aircraftService.getSeatByRegistration(aircraft.getRegistration()) + ")");
            }

//        List<CrewMember> crew = crewRepo.findAllById(flightRequest.getCrewIds());

            Flight flight = new Flight();
            flight.setFlightCode(flightRequest.getFlightCode());
            flight.setDepartureTime(flightRequest.getDepartureTime());
            flight.setArrivalTime(flightRequest.getArrivalTime());
            flight.setDepartureAirport(departure);
            flight.setArrivalAirport(arrival);
            flight.setStatus(flightRequest.getStatus());
            flight.setAircraft(aircraft);
            flight.setCreateAt(LocalDateTime.now());
            flight.setAirline(airlines);
//        flight.setCrew(crew);
            flight.setIsDeleted(false);
            flight.setArrivalGate(flightRequest.getArrivalGate());
            flight.setDepartureGate(flightRequest.getDepartureGate());
            flight.setPriceBusiness(flightRequest.getPriceBusiness());
            flight.setPriceEconomy(flightRequest.getPriceEconomy());
            flight.setCheckInDeadline(flightRequest.getCheckInDeadline());
            flight.setBoardingTime(flightRequest.getBoardingTime());
            flight.setSeats(flightRequest.getSeats());
            flight.setAvailableSeats(flightRequest.getSeats());


            Flight savedFlight = flightRepository.save(flight);
            return convertToFlightResponse(savedFlight);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FlightsResponse updateFlight(Long id, FlightRequest flightRequest) {
        Aircraft aircraft = aircraftService.getByRegistration(flightRequest.getAircraftRegistration());
        if (aircraft == null) {
            throw new BookingException(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED, messageCommon.getMessage(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED));
        }
        if (flightRequest.getSeats() > aircraftService.getSeatByRegistration(aircraft.getRegistration())) {
            throw new BookingException("Error", "Ghế được yêu cầu cập nhật (" + flightRequest.getSeats() + ") vượt quá chỗ ngồi có sẵn (" + aircraftService.getSeatByRegistration(aircraft.getRegistration()) + ")");
        }
        Flight flight = flightRepository.findById(id).orElseThrow();
        flight.setFlightCode(flightRequest.getFlightCode());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setArrivalTime(flightRequest.getArrivalTime());
        flight.setStatus(flightRequest.getStatus());
        flight.setUpdateAt(LocalDateTime.now());
        flight.setSeats(flightRequest.getSeats());
        flight.setArrivalGate(flightRequest.getArrivalGate());
        flight.setDepartureGate(flightRequest.getDepartureGate());
        flight.setPriceBusiness(flightRequest.getPriceBusiness());
        flight.setPriceEconomy(flightRequest.getPriceEconomy());
        flight.setCheckInDeadline(flightRequest.getCheckInDeadline());
        flight.setBoardingTime(flightRequest.getBoardingTime());
        if (!flight.getStatus().equals(flightRequest.getStatus())) {
            flightHistoryRepository.save(FlightHistory.builder()
                    .updateAt(LocalDateTime.now())
                    .statusReason(flightRequest.getStatusReason())
                    .flight(flight)
                    .status(flightRequest.getStatus())
                    .build());
        }
        Flight savedFlight = flightRepository.save(flight);
        return convertToFlightResponse(savedFlight);
    }

    @Override
    public String deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        flight.setIsDeleted(true);
        flight.setUpdateAt(LocalDateTime.now());
        flightRepository.save(flight);
        return "Xóa chuyến bay thành công";
    }

    public FlightsResponse convertToFlightResponse(Flight flight) {
        return FlightsResponse.builder()
                .id(flight.getId())
                .flightCode(flight.getFlightCode())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .departureAirport(flight.getDepartureAirport() != null ? flight.getDepartureAirport().getAirport() : null)
                .departureAirportId(flight.getDepartureAirport() != null ? flight.getDepartureAirport().getId() : null)
                .arrivalAirport(flight.getArrivalAirport() != null ? flight.getArrivalAirport().getAirport() : null)
                .arrivalAirportId(flight.getArrivalAirport() != null ? flight.getArrivalAirport().getId() : null)
                .status(flight.getStatus())
                .aircraft(flight.getAircraft() != null ? flight.getAircraft().getRegistration() : null)
                .isDeleted(flight.getIsDeleted())
                .createAt(flight.getCreateAt())
                .updateAt(flight.getUpdateAt())
                .seats(flight.getSeats())
                .airlineId(flight.getAirline().getId())
                .airlineName(flight.getAirline().getName())
                .airlineCode(flight.getAirline().getCode())
                .availableSeats(flight.getAvailableSeats())
                .priceEconomy(flight.getPriceEconomy())
                .priceBusiness(flight.getPriceBusiness())
                .departureGate(flight.getDepartureGate())
                .arrivalGate(flight.getArrivalGate())
                .checkInDeadline(flight.getCheckInDeadline())
                .boardingTime(flight.getBoardingTime())
                .build();
    }

    @Override
    public Page<FlightsResponse> searchFlightsForDirection(int page, int size, LocalDate date, String departureCity, String arrivalCity) {
        if (arrivalCity == null || arrivalCity.isEmpty() || departureCity == null || departureCity.isEmpty()) {
            throw new BookingException(ServiceMessageConstants.CHOOSE_DESTINATION_AND_PLACE,
                    messageCommon.getMessage(ServiceMessageConstants.CHOOSE_DESTINATION_AND_PLACE));
        }

        List<AirportInfo> arrivalAirports = airPortInfoService.findAllByCity(arrivalCity);
        List<AirportInfo> departureAirports = airPortInfoService.findAllByCity(departureCity);

        if (arrivalAirports.isEmpty() || departureAirports.isEmpty()) {
            throw new BookingException(ServiceMessageConstants.AIRPORT_NOT_FOUND,
                    messageCommon.getMessage(ServiceMessageConstants.AIRPORT_NOT_FOUND));
        }

        LocalDateTime startOfDay = date.atStartOfDay();

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "departureTime");
        Page<Flight> flightsPage = flightRepository.findByDepartureAirport_CityAndArrivalAirport_CityAndDepartureTimeAfter(
                departureCity, arrivalCity, startOfDay, pageable);

        return convertToFlightResponse(flightsPage);
    }


    @Override
    public FlightsResponse getFlightById(Long id)  {
        Flight flight = flightRepository.findById(id).orElse(null);
        return convertToFlightResponse(flight);
    }

    @Override
    public Flight getFlightByIdFlight(Long id) throws Exception {
        return flightRepository.findById(id).orElse(null);
    }

    @Override
    public List<Flight> getFlightByStatus(String status) {
        return List.of();
    }

    public List<Flight> getFlightByStatus(FlightStateEnum status) {
        if (status == null) {
            return flightRepository.findAll();
        }
        return null;
    }


    @Override
    public int getSeat(Long id) {
        Flight flight = flightRepository.findById(id).orElse(null);
        assert flight != null;

        return flight.getAircraft().getType().getSeatCapacity();
    }

//    @Override
//    public List<Airlines> getAllAriline() {
//        return airlinesService.findAll();
//    }

    @Override
    public Page<FlightsResponse> getAllFlights(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Flight> flights = flightRepository.findAllByIsDeleted(false, pageable);
        return flights.map(this::convertToFlightResponse);
    }

    public Page<FlightsResponse> convertToFlightResponse(Page<Flight> flightPage) {
        List<FlightsResponse> dtos = flightPage.stream()
                .map(this::convertToFlightResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, flightPage.getPageable(), flightPage.getTotalElements());
    }
}
