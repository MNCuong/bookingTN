package com.example.booking.Service.Impl;

import com.example.booking.DTO.Request.FlightRequestPackage.BookingFlRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.PassengerInfo;
import com.example.booking.DTO.Response.FlightResponsePackage.PassengerResponse;
import com.example.booking.DTO.Response.FlightResponsePackage.TicketResponse;
import com.example.booking.Entity.*;
import com.example.booking.Repository.PassengerRepository;
import com.example.booking.Service.*;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepo;
    private final TicketService ticketService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final FlightService flightService;
    private final PaymentService paymentService;

    @Override
    public Passenger createPassenger(Passenger passenger, HttpServletRequest request) {
        String token = JwtUtil.getTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        User user = userService.findUserByEmail(email);
        if (passenger.getNationality().equals("VietNam")) {
            if (passenger.getNationalId() == null || passenger.getNationalId().isEmpty()) {
                throw new IllegalArgumentException("National ID is required for Vietnamese passengers");
            }
        } else {
            if (passenger.getPassportNumber() == null || passenger.getPassportNumber().isEmpty()) {
                throw new IllegalArgumentException("Passport number is required for international passengers");
            }
        }
        passenger.setUser(user);

        return passengerRepo.save(passenger);
    }


    @Override
    public Passenger updatePassenger(Long id, Passenger passenger) {
        Passenger existingPassenger = passengerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        existingPassenger.setFullName(passenger.getFullName());
        existingPassenger.setPassportNumber(passenger.getPassportNumber());
        existingPassenger.setNationality(passenger.getNationality());
        existingPassenger.setEmail(passenger.getEmail());
        existingPassenger.setBirthDate(passenger.getBirthDate());

        return passengerRepo.save(existingPassenger);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepo.deleteById(id);
    }

    @Override
    public Page<PassengerResponse> getList(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Passenger> passengerPage;
        if (search == null || search.trim().isEmpty()) {
            passengerPage = passengerRepo.findAll(pageable);
        } else {
            passengerPage = passengerRepo.findByAnyField(search, pageable);
        }
        return passengerPage.map(passenger -> {
            PassengerResponse response = new PassengerResponse();
            response.setId(passenger.getId());
            response.setFullName(passenger.getFullName());
            response.setEmail(passenger.getEmail());
            response.setNationalId(passenger.getIdentification());
            response.setNationality(passenger.getNationality());
            return response;
        });
    }

    @Override
    public List<TicketResponse> getTicketsByPassengerId(Long id) {
        return ticketService.getTicketsByPassengerId(id);
    }

    @Override
    public List<Passenger> findByUser(User user) {
        return passengerRepo.findByUser(user);
    }

    @Override
    public Page<TicketResponse> getList(int page, int size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//        Page<Ticket> ticketPage = ticketRepo.findAll(pageable);
        String token = JwtUtil.getTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        User user = userService.findUserByEmail(email);
        List<Passenger> passengers = findByUser(user);

        if (passengers.isEmpty()) {
            return Page.empty();
        }

        Page<Ticket> ticketPage = ticketService.findAllByPassengers(passengers, pageable);

        return ticketPage.map(ticket -> {
            TicketResponse response = new TicketResponse();
            response.setId(ticket.getId());
            response.setSeatNumber(ticket.getSeatNumber());
            response.setClassType(ticket.getClassType());
            response.setPrice(ticket.getPrice());
            response.setCheckedIn(ticket.isCheckedIn());
            response.setPassengerId(ticket.getPassenger().getId());
            response.setPassengerFullName(ticket.getPassenger().getFullName());
            response.setFlightId(ticket.getFlight() != null ? ticket.getFlight().getId() : null);
            response.setFlightCode(ticket.getFlight() != null ? ticket.getFlight().getFlightCode() : null);
            response.setTransactionNo(ticket.getPaymentTransaction().getTransactionNo() != null ? ticket.getPaymentTransaction().getTransactionNo() : null);
            response.setCreatedAt(ticket.getCreatedAt() != null ? ticket.getCreatedAt() : null);
            response.setCheckinDate(ticket.getCheckinDate() != null ? ticket.getCheckinDate() : null);
            return response;
        });
    }

    @Override
    @Transactional
    public void saveBooking(BookingFlRequest bookingFlRequest, HttpServletRequest request) throws Exception {
        String token = JwtUtil.getTokenFromRequest(request);
        User user = userService.findUserByEmail(jwtUtil.extractUsername(token));
        List<Passenger> passengers = new ArrayList<>();
        for (PassengerInfo info : bookingFlRequest.getPassengerInfos()) {
            Passenger passenger = new Passenger();
            log.info("gender: {}", info.getPassenger().getGender());
            passenger.setUser(user);
            passenger.setFullName(info.getPassenger().getFullName());
            passenger.setPassportNumber(info.getPassenger().getPassportNumber());
            passenger.setNationalId(info.getPassenger().getNationalId());
            passenger.setNationality(info.getPassenger().getNationality());
            passenger.setEmail(info.getPassenger().getEmail());
            passenger.setBirthDate(LocalDate.parse(info.getPassenger().getBirthDate()));
            passenger.setGender("0".equals(info.getPassenger().getGender()));
            passenger.setPrice(info.getPrice());

            Passenger savedPassenger = passengerRepo.save(passenger);
            passengers.add(savedPassenger);

            Ticket ticket = new Ticket();
            Flight flight = flightService.getFlightByIdFlight(bookingFlRequest.getFlightId());
            PaymentTransaction paymentTransaction = paymentService.findByVnp_TransactionNo(bookingFlRequest.getTransactionNo());
            ticket.setSeatNumber(info.getPassenger().getNumber());
            ticket.setClassType(info.getPassenger().getType());
            ticket.setPrice(info.getPrice());
            ticket.setCheckedIn(false);
            ticket.setPassenger(savedPassenger);
            ticket.setFlight(flight);
            ticket.setPaymentTransaction(paymentTransaction);
            ticket.setCreatedAt(LocalDateTime.now());

            ticketService.save(ticket);
        }
    }
}
