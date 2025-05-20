package com.example.booking.Controller.Flight;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.BookingFlRequest;
import com.example.booking.DTO.Response.FlightResponsePackage.PassengerResponse;
import com.example.booking.DTO.Response.FlightResponsePackage.TicketResponse;
import com.example.booking.Entity.CrewMember;
import com.example.booking.Entity.Passenger;
import com.example.booking.Entity.Ticket;
import com.example.booking.Service.PassengerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<Page<PassengerResponse>>> getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String search) {
        return ResponseConfig.success(passengerService.getList(page, size, search));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Passenger>> createPassenger(@RequestBody Passenger passenger, HttpServletRequest request) {
        Passenger savedPassenger = passengerService.createPassenger(passenger, request);
        return ResponseConfig.success(savedPassenger);
    }

    @PostMapping("booking")
    public ResponseEntity<ResponseDto<String>> createPassenger(@RequestBody BookingFlRequest bookingFlRequest,HttpServletRequest request) throws Exception {
        passengerService.saveBooking(bookingFlRequest,request);
        return ResponseConfig.success("Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Passenger>> updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        Passenger updatedPassenger = passengerService.updatePassenger(id, passenger);
        return ResponseConfig.success(updatedPassenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<ResponseDto<List<TicketResponse>>> getTicketsByPassenger(@PathVariable Long id) {
        List<TicketResponse> tickets = passengerService.getTicketsByPassengerId(id);
        return ResponseConfig.success(tickets);
    }

    @GetMapping("/ticket")
    public ResponseEntity<ResponseDto<Page<TicketResponse>>> getTicketsByPassenger(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(name = "size", defaultValue = "10") int size, HttpServletRequest request) {
        Page<TicketResponse> tickets = passengerService.getList(page, size, request);
        return ResponseConfig.success(tickets);
    }

}
