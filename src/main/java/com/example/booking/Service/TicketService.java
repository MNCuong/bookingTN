package com.example.booking.Service;

import com.example.booking.DTO.Response.FlightResponsePackage.TicketResponse;
import com.example.booking.Entity.Passenger;
import com.example.booking.Entity.Ticket;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TicketService {
    Ticket createTicket(Ticket request);

    List<TicketResponse> getTicketsByPassengerId(Long passengerId);

    List<TicketResponse> getTicketsFlight(Long flightId);

    Ticket updateTicket(Long id, Ticket request);

    void deleteTicket(Long id);

    Page<TicketResponse> getList(int page, int size, String search);

    //    Page<TicketResponse> getList(int page, int size, HttpServletRequest request);
    Page<Ticket> findAllByPassengers(List<Passenger> passengers, Pageable pageable);
    void save(Ticket ticket);
    List<Map<String, Object>> getTicketsGroupedByTransaction(Long userId);
    String checkin(Long id);
}
