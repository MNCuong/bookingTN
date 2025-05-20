package com.example.booking.Service.Impl;

import com.example.booking.DTO.Response.FlightResponsePackage.FlightsResponse;
import com.example.booking.DTO.Response.FlightResponsePackage.PassengerResponse;
import com.example.booking.DTO.Response.FlightResponsePackage.TicketResponse;
import com.example.booking.Entity.*;
import com.example.booking.Repository.TicketRepository;
import com.example.booking.Service.FlightService;
import com.example.booking.Service.PassengerService;
import com.example.booking.Service.TicketService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepo;
    private final FlightService flightService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public Ticket createTicket(Ticket ticket) {
        ticket.setCreatedAt(LocalDateTime.now());
        return ticketRepo.save(ticket);
    }

    @Override
    public List<TicketResponse> getTicketsByPassengerId(Long passengerId) {
        List<Ticket> tickets = ticketRepo.getTicketsByPassenger_Id(passengerId);
        return tickets.stream().map(ticket -> {
            TicketResponse response = new TicketResponse();
            response.setId(ticket.getId());
            response.setSeatNumber(ticket.getSeatNumber());
            response.setClassType(ticket.getClassType());
            response.setPrice(ticket.getPrice());
            response.setCheckedIn(ticket.isCheckedIn());
            response.setCreatedAt(ticket.getCreatedAt());
            response.setCheckinDate(ticket.getCheckinDate());

            // Gán thông tin Passenger
            if (ticket.getPassenger() != null) {
                response.setPassengerId(ticket.getPassenger().getId());
                response.setPassengerFullName(ticket.getPassenger().getFullName());
            }

            if (ticket.getFlight() != null) {
                response.setFlightId(ticket.getFlight().getId());
                response.setFlightCode(ticket.getFlight().getFlightCode());
            }
            if (ticket.getPaymentTransaction() != null) {
                response.setTransactionNo(ticket.getPaymentTransaction().getTransactionNo());
            }

            return response;
        }).toList();
    }

    @Override
    public List<TicketResponse> getTicketsFlight(Long flightId) {
        FlightsResponse flight = flightService.getFlightById(flightId);
        List<Ticket> ticketResponse = ticketRepo.findByFlight_Id(flight.getId());
        return ticketResponse.stream().map(ticket -> {
            TicketResponse response = new TicketResponse();
            response.setId(ticket.getId());
            response.setSeatNumber(ticket.getSeatNumber());
            response.setClassType(ticket.getClassType());
            response.setPrice(ticket.getPrice());
            response.setCheckedIn(ticket.isCheckedIn());
            response.setCreatedAt(ticket.getCreatedAt());
            response.setCheckinDate(ticket.getCheckinDate());

            // Gán thông tin Flight
            if (ticket.getPassenger() != null) {
                response.setPassengerId(ticket.getPassenger().getId());
                response.setPassengerFullName(ticket.getPassenger().getFullName());
            }

            if (ticket.getFlight() != null) {
                response.setFlightId(ticket.getFlight().getId());
                response.setFlightCode(ticket.getFlight().getFlightCode());
            }
            if (ticket.getPaymentTransaction() != null) {
                response.setTransactionNo(ticket.getPaymentTransaction().getTransactionNo());
            }

            return response;
        }).toList();
    }

    @Override
    public Ticket updateTicket(Long id, Ticket ticket) {
        Ticket existingTicket = ticketRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        existingTicket.setSeatNumber(ticket.getSeatNumber());
        existingTicket.setClassType(ticket.getClassType());
        existingTicket.setPrice(ticket.getPrice());
        existingTicket.setCheckedIn(ticket.isCheckedIn());

        return ticketRepo.save(existingTicket);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepo.deleteById(id);
    }

    @Override
    public Page<TicketResponse> getList(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Ticket> ticketPage;
        if (search == null || search.isEmpty()) {
            ticketPage = ticketRepo.findAll(pageable);

        } else {
            ticketPage = ticketRepo.searchTickets(search, pageable);

        }

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
            response.setTransactionNo(ticket.getPaymentTransaction() != null ? ticket.getPaymentTransaction().getTransactionNo() : null);
            response.setCreatedAt(ticket.getCreatedAt() != null ? ticket.getCreatedAt() : null);
            response.setCheckinDate(ticket.getCheckinDate() != null ? ticket.getCheckinDate() : null);
            return response;
        });
    }

    @Override
    public Page<Ticket> findAllByPassengers(List<Passenger> passengers, Pageable pageable) {
        return ticketRepo.findAllByPassengers(passengers, pageable);
    }

    @Override
    public void save(Ticket ticket) {
        ticketRepo.save(ticket);
    }


    public List<Map<String, Object>> getTicketsGroupedByTransaction(Long userId) {
        List<Ticket> tickets = ticketRepo.findTicketsByUserId(userId);

        Map<Long, Map<String, Object>> groupedTickets = new LinkedHashMap<>();

        for (Ticket ticket : tickets) {
            PaymentTransaction transaction = ticket.getPaymentTransaction();
            Long transactionId = transaction.getId();

            if (!groupedTickets.containsKey(transactionId)) {
                groupedTickets.put(transactionId, new HashMap<>() {{
                    put("transactionId", transactionId);
                    put("transactionNo", transaction.getTransactionNo());
                    put("amount", transaction.getAmount());
                    put("transactionDate", transaction.getTransactionDate());
                    put("flight", convertToFlightResponse(ticket.getFlight()));
                    put("tickets", new ArrayList<>());
                }});
            }

            Map<String, Object> ticketData = new HashMap<>();
            ticketData.put("ticketId", ticket.getId());
            ticketData.put("seatNumber", ticket.getSeatNumber());
            ticketData.put("classType", ticket.getClassType());
            ticketData.put("price", ticket.getPrice());
            ticketData.put("passengerName", ticket.getPassenger().getFullName());

            List<Map<String, Object>> ticketList = (List<Map<String, Object>>) groupedTickets.get(transactionId).get("tickets");
            ticketList.add(ticketData);
        }

        return new ArrayList<>(groupedTickets.values());
    }

    @Override
    public String checkin(Long id) {
        Ticket ticket=ticketRepo.findById(id).get();
        if(ticket==null){
            throw new RuntimeException("Ticket not found");
        }else{
            ticket.setCheckedIn(true);
            ticket.setCheckinDate(LocalDateTime.now());
            ticketRepo.save(ticket);
            return "Success";
        }
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
                .availableSeats(flight.getAvailableSeats())
                .priceEconomy(flight.getPriceEconomy())
                .priceBusiness(flight.getPriceBusiness())
                .departureGate(flight.getDepartureGate())
                .arrivalGate(flight.getArrivalGate())
                .checkInDeadline(flight.getCheckInDeadline())
                .boardingTime(flight.getBoardingTime())
                .build();
    }
}