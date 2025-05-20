package com.example.booking.Controller.Customer.FlightCustomer;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.BookingRequest;
import com.example.booking.DTO.Response.BookingResponse;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.AirportInfo;
import com.example.booking.Entity.BookingFl;
import com.example.booking.Entity.Flight;
import com.example.booking.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/booking-flight")
@RequiredArgsConstructor
public class BookingFlightController {

    private final BookingFlService bookingFlService;
    private final FlightService flightService;
    private final AirPortInfoService airPortInfoService;
    private final AircraftService aircraftService;
    private final BookingFlightService bookingFlightService;

    @PostMapping("/order-ticket")
    public ResponseEntity<ResponseDto<String>> createBookingFlight(@RequestBody BookingRequest request, HttpServletRequest httpServletRequest) throws Exception {
        Flight flight = flightService.getFlightByIdFlight(request.getFlightId());
        if (flight == null) {
            return ResponseConfig.error("Flight not found");
        }
        Aircraft aircraft = flight.getAircraft();
        AirportInfo depaAirportInfo = flight.getDepartureAirport();
        AirportInfo arriAirportInfo = flight.getArrivalAirport();

//        for (Map.Entry<String, BookingRequest.PassengerInfos> entry : request.getPassengerInfos().entrySet()) {
//            String seatNumber = entry.getKey();
//            BookingRequest.PassengerInfos info = entry.getValue();
//
//            BookingFl booking = new BookingFl();
//            booking.setCustomerName(info.getFullName());
//            booking.setDateOfBirth(info.getDateOfBirth());
//            booking.setGender(info.getGender());
//            booking.setNationality(info.getNationality());
//            booking.setPersonalCode(info.getPersonalCode());
//            booking.setSeatNumber(seatNumber);
//            booking.setTicketType(info.getTicketType());
//            booking.setPrice(info.getPrice());
//            booking.setFlight(flight);
//            booking.setAircraft(aircraft);
//            booking.setDeparture(depaAirportInfo);
//            booking.setArrival(arriAirportInfo);
//            booking.setCreatedAt(LocalDateTime.now());
//            booking.setFlightDate(flight.getCreateAt());
//
//            booking.setTransactionId(request.getTransactionId());

//            bookingFlightService.save(booking, httpServletRequest);
//        }


        return ResponseConfig.success("Đặt vé thành công");
    }

    @GetMapping("/list-booking")
    public ResponseEntity<ResponseDto<Page<BookingFl>>> listBookings(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseConfig.success(bookingFlightService.getAllBooking(page, size));

    }
    @GetMapping("/booking-detail/{id}")
    public ResponseEntity<ResponseDto<BookingFl>> bookingDetail(@PathVariable Long id) {
        return ResponseConfig.success(bookingFlightService.getBooking(id));

    }
}
