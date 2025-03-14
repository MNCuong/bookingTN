package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Event.FlightBookingEvent;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightBookingRequest;
import com.example.booking.DTO.Response.FlightResponsePackage.FlightBookingResponse;
import com.example.booking.Entity.FlightBooking;
import com.example.booking.Entity.User;
import com.example.booking.Entity.UserProfileFlight;
import com.example.booking.Enum.AircraftTypeEnums;
import com.example.booking.Enum.FlightStateEnum;
import com.example.booking.Enum.TypeServiceEnum;
import com.example.booking.Exception.BookingException;
import com.example.booking.Manager.FlightSeatManager;
import com.example.booking.Repository.FlightBookingRepository;
import com.example.booking.Service.FlightBookingService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class FlightBookingServiceImpl implements FlightBookingService {
    @Value("${aviationstack.url}")
    private String API_URL;
    @Value("${aviationstack.key}")
    private String API_KEY;

    private final RestTemplate restTemplate = new RestTemplate();
    private final JwtUtil jwtUtil;
    private final FlightBookingRepository flightBookingRepository;
    private final UserService userService;
    private final FlightSeatManager flightSeatManager;
    private final ServiceCommon serviceCommon;
    private final MessageCommon messageCommon;
    private final KafkaTemplate<String,FlightBookingEvent> kafkaTemplate;

    @Override
    public FlightBooking findById(long id) {
        return flightBookingRepository.findById(id).orElse(null);
    }

    @Override
    public void save(FlightBooking flightBooking) {
        flightBookingRepository.save(flightBooking);
    }


    @Override
    public String searchFlights(String depIata, String arrIata) {
        RestTemplate restTemplate = new RestTemplate();
        String url;

        url = API_URL + "?access_key=" + API_KEY + "&dep_iata=" + depIata + "&arr_iata=" + arrIata;
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

    @Override
    public FlightBooking findByBookingId(String id) {
        return flightBookingRepository.findByBookingId(id);
    }

    @Override
    public int getAvailableSeats(String flightCode, String aircraftModel) {
        int bookedSeats = flightBookingRepository.countByFlightCodeAndStatus(flightCode, "CONFIRMED");
        boolean flightExists = flightBookingRepository.existsByFlightCode(flightCode);
        int totalSeats = AircraftTypeEnums.getSeatsByModel(aircraftModel);

        if (!flightExists) {
            return totalSeats;
        }

        return totalSeats - bookedSeats;
    }

    @Transactional
    @Override
    public FlightBookingResponse bookFlight(FlightBookingRequest flightBookingRequest, HttpServletRequest httpServletRequest) {
        int availableSeats = flightSeatManager.getAvailableSeats(flightBookingRequest.getFlightCode(), flightBookingRequest.getAircraftModel(), flightBookingRequest.getFlightDate(), flightBookingRequest.getDepartureTime(), flightBookingRequest.getArrivalTime());
        LocalDate flightDate = flightBookingRequest.getFlightDate();
        if (availableSeats < flightBookingRequest.getSeatTotal()) {
            throw new BookingException(ServiceMessageConstants.NOT_ENOUGH_SEAT, messageCommon.getMessage(ServiceMessageConstants.NOT_ENOUGH_SEAT));
        }
        LocalTime departureTime = flightBookingRequest.getDepartureTime();
        LocalTime arrivalTime = flightBookingRequest.getArrivalTime();

        List<String> bookedSeats = flightBookingRepository.findConfirmedSeats(flightBookingRequest.getFlightCode(), flightDate, departureTime, arrivalTime);

        List<String> seatNumbers = flightBookingRequest.getSeatNumber();
        for (String seat : seatNumbers) {
            if (bookedSeats.contains(seat)) {
                throw new BookingException(ServiceMessageConstants.SEAT_ALREADY_BOOKED, "Ghế " + seat + " đã được đặt!");
            }
        }

        flightSeatManager.bookSeats(flightBookingRequest.getFlightCode(), flightDate, departureTime, arrivalTime, flightBookingRequest.getSeatTotal());

        String token = JwtUtil.getTokenFromRequest(httpServletRequest);
        if (token == null || token.isEmpty()) {
            throw new BookingException(ServiceMessageConstants.INVALID_TOKEN, messageCommon.getMessage(ServiceMessageConstants.INVALID_TOKEN));
        }

        String email = jwtUtil.extractUsername(token);
        if (email == null || email.isEmpty()) {
            throw new BookingException(ServiceMessageConstants.INVALID_TOKEN_USER, messageCommon.getMessage(ServiceMessageConstants.INVALID_TOKEN_USER));
        }
        User user=userService.findUserByEmail(email);


        List<FlightBooking> bookings = new ArrayList<>();
        String bookingId = serviceCommon.generateBookingId();
        int index = 0;
        BigDecimal totalPrice = flightBookingRequest.getTotalPrice();
        int totalSeats = flightBookingRequest.getSeatTotal();
        int childCount = 0;
        int adultCount = 0;
        for (UserProfileFlight userProfileFlightCount : flightBookingRequest.getUserProfileFlight()) {
            Boolean childValue = userProfileFlightCount.getChild();
            if (childValue == null) {
                throw new BookingException(ServiceMessageConstants.CHILD_ONLY_RECEIVES_TRUE_OR_FALSE, messageCommon.getMessage(ServiceMessageConstants.CHILD_ONLY_RECEIVES_TRUE_OR_FALSE));
            }
            if (userProfileFlightCount.getChild()) {
                childCount++;
            } else {
                adultCount++;

            }
        }
        if (adultCount == 0) {
            throw new BookingException(ServiceMessageConstants.DIVISION_BY_ZERO,messageCommon.getMessage(ServiceMessageConstants.DIVISION_BY_ZERO));
        }

        BigDecimal childPricePerSeat = BigDecimal.ZERO;
        BigDecimal adultPricePerSeat = BigDecimal.ZERO;
        adultPricePerSeat = calculateBaseAdultPrice(totalPrice.doubleValue(), adultCount, childCount);
        log.info("Gia nguoi lon: {}", adultPricePerSeat);
        if (childCount > 0) {
            childPricePerSeat = (totalPrice.subtract(adultPricePerSeat.multiply(BigDecimal.valueOf(adultCount)))).divide(BigDecimal.valueOf(childCount), 2, BigDecimal.ROUND_HALF_UP);
            log.info("Gia tre em: {}", childPricePerSeat);
        }
        FlightBooking booking = null;

        for (UserProfileFlight userProfileFlight : flightBookingRequest.getUserProfileFlight()) {
            if (index >= seatNumbers.size()) {
                throw new BookingException(ServiceMessageConstants.NOT_ENOUGH_SEAT, messageCommon.getMessage(ServiceMessageConstants.NOT_ENOUGH_SEAT));
            }

            BigDecimal pricePerSeat;
            if (userProfileFlight.getChild()) {
                pricePerSeat = childPricePerSeat;
            } else {
                pricePerSeat = adultPricePerSeat;
            }
            try {
                 booking = flightBookingRepository.save(FlightBooking.builder()
                        .flightCode(flightBookingRequest.getFlightCode())
                        .user(user)
                        .bookingId(bookingId)
                        .createdAt(LocalDateTime.now())
                        .seatNumber(seatNumbers.get(index))
                        .flightDate(flightBookingRequest.getFlightDate())
                        .totalPrice(pricePerSeat)
                        .departureTime(departureTime)
                        .arrivalTime(arrivalTime)
                        .status(FlightStateEnum.PENDING.toString())
                        .build());
                bookings.add(booking);
            } catch (Exception e) {
                throw new BookingException(ServiceMessageConstants.DATABASE_ERROR, messageCommon.getMessage(ServiceMessageConstants.DATABASE_ERROR));
            }

            index++;
        }
        assert booking != null;
        FlightBookingEvent flightBookingEvent=FlightBookingEvent.builder()
                .userEmail(user.getEmail())
                .typeService(TypeServiceEnum.FLIGHT)
                .totalPrice(flightBookingRequest.getTotalPrice())
                .bookingId(booking.getBookingId()).build();
        kafkaTemplate.send("flight_booking_topic", flightBookingEvent);

        return FlightBookingResponse.builder()
                .flightBookingId(bookingId)
                .message("Đang xử lý!")
                .status(FlightStateEnum.PENDING.toString())
                .build();
    }

    public static BigDecimal calculateBaseAdultPrice(double totalAmount, int numAdults, int numChildren) {
        double totalRatio = (numAdults * 1.0) + (numChildren * 0.75);
        return BigDecimal.valueOf(totalAmount / totalRatio);
    }
}


