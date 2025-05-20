package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.BookingFlRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.BookingRequest;
import com.example.booking.DTO.Response.FlightResponsePackage.PassengerResponse;
import com.example.booking.DTO.Response.FlightResponsePackage.TicketResponse;
import com.example.booking.Entity.Passenger;
import com.example.booking.Entity.Ticket;
import com.example.booking.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PassengerService {
    Passenger createPassenger(Passenger passenger, HttpServletRequest request);
    Passenger updatePassenger(Long id, Passenger passenger);
    void deletePassenger(Long id);
    Page<PassengerResponse> getList(int page, int size, String search);
    List<TicketResponse> getTicketsByPassengerId(Long id);
    List<Passenger> findByUser(User user);
    Page<TicketResponse> getList(int page, int size, HttpServletRequest request);
    void saveBooking(BookingFlRequest bookingFlRequest, HttpServletRequest request) throws Exception;

}
