package com.example.booking.Service.Impl;

import com.example.booking.Common.ServiceCommon;
import com.example.booking.DTO.Request.FlightRequestPackage.BookingFlRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.PassengerInfo;
import com.example.booking.DTO.Request.FlightRequestPackage.TicketInfo;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private final EmailService emailService;

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
        Flight departureFlight = new Flight();
        Flight returnFlight = new Flight();
        if (bookingFlRequest.getDepartureFlightId() != null) {
            departureFlight = flightService.getFlightByIdFlight(bookingFlRequest.getDepartureFlightId());
        }
        if (bookingFlRequest.getReturnFlightId() != null) {
            returnFlight = flightService.getFlightByIdFlight(bookingFlRequest.getReturnFlightId());
        }
        List<TicketInfo> departureTicketInfos = createTickets(
                bookingFlRequest.getDepartureSeats(),
                departureFlight.getPriceBusiness(),
                departureFlight.getPriceEconomy()
        );

        List<TicketInfo> returnTicketInfos = bookingFlRequest.getReturnFlightId() != null
                ? createTickets(bookingFlRequest.getReturnSeats(), returnFlight.getPriceBusiness(), returnFlight.getPriceEconomy())
                : Collections.emptyList();

        PaymentTransaction paymentTransaction = paymentService.findByVnp_TransactionNo(bookingFlRequest.getTransactionNo());
        for (int i = 0; i < bookingFlRequest.getPassengerInfos().size(); i++) {
            TicketInfo departureTicketInfo = departureTicketInfos.get(i);
            TicketInfo returnTicketInfo = returnTicketInfos.size() > i ? returnTicketInfos.get(i) : null;
            String tripType;
            if (bookingFlRequest.getDepartureFlightId() != null && bookingFlRequest.getReturnFlightId() != null) {
                tripType = "roundtrip";
            } else {
                tripType = "oneway";
            }
            PassengerInfo info = bookingFlRequest.getPassengerInfos().get(i);
            Passenger passenger = new Passenger();
            passenger.setUser(user);
            passenger.setFullName(info.getName());
            passenger.setPassportNumber("");
            passenger.setNationalId(info.getNationalId());
            passenger.setNationality(info.getNationality());
            passenger.setEmail(info.getEmail());
            passenger.setBirthDate(info.getDateOfBirth());
            passenger.setGender(info.getGender().equals("1"));
            passenger.setType(tripType);

            Passenger savedPassenger = passengerRepo.save(passenger);
//            passengers.add(savedPassenger);

            if (bookingFlRequest.getDepartureFlightId() != null) {
                Ticket ticketDeparture = new Ticket();
                ticketDeparture.setSeatNumber(departureTicketInfo.getNumber());
                ticketDeparture.setClassType(departureTicketInfo.getType());
                ticketDeparture.setTripType(tripType);
                ticketDeparture.setPrice(new BigDecimal(departureTicketInfo.getPrice().toString()));
                ticketDeparture.setCheckedIn(false);
                ticketDeparture.setPassenger(savedPassenger);
                ticketDeparture.setFlight(departureFlight);
                ticketDeparture.setPaymentTransaction(paymentTransaction);
                ticketDeparture.setCreatedAt(LocalDateTime.now());
                ticketService.save(ticketDeparture);
            }

            if (bookingFlRequest.getReturnFlightId() != null && returnTicketInfo != null) {
                Ticket ticketReturn = new Ticket();
                ticketReturn.setSeatNumber(returnTicketInfo.getNumber());
                ticketReturn.setClassType(returnTicketInfo.getType());
                ticketReturn.setTripType(tripType);
                ticketReturn.setPrice(new BigDecimal(returnTicketInfo.getPrice().toString()));
                ticketReturn.setCheckedIn(false);
                ticketReturn.setPassenger(savedPassenger);
                ticketReturn.setFlight(returnFlight);
                ticketReturn.setPaymentTransaction(paymentTransaction);
                ticketReturn.setCreatedAt(LocalDateTime.now());
                ticketService.save(ticketReturn);
            }

        }

    }

    private List<TicketInfo> createTickets(List<String> seats, Double priceBusiness, Double priceEconomy) {
        return seats.stream().map(seat -> {
            int seatNumber = Integer.parseInt(seat.replaceAll("\\D+", ""));
            String type = (seatNumber >= 1 && seatNumber <= 5) ? "Business" : "Economy";
            Double price = type.equals("Business") ? priceBusiness : priceEconomy;

            return new TicketInfo(seat, type, price);
        }).collect(Collectors.toList());
    }

}
