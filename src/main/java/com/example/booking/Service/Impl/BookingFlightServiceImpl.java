package com.example.booking.Service.Impl;

import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.BookingFl;
import com.example.booking.Entity.FlightBooking;
import com.example.booking.Entity.User;
import com.example.booking.Repository.BookingFlRepository;
import com.example.booking.Repository.FlightBookingRepository;
import com.example.booking.Service.BookingFlightService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BookingFlightServiceImpl implements BookingFlightService {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final FlightBookingRepository flightBookingRepository;
    private final BookingFlRepository bookingFlRepository;

    @Override
    public List<BookingFl> getListBooking(String email) {
        User user = userService.findUserByEmail(email);
        return bookingFlRepository.findByUserOrderByCreateAtAsc(user);
    }

    @Override
    public String save(BookingFl bookingFl, HttpServletRequest httpServletRequest) {
        String token = JwtUtil.getTokenFromRequest(httpServletRequest);
        User user = userService.findUserByEmail(jwtUtil.extractUsername(token));
        bookingFl.setUser(user);
        bookingFlRepository.save(bookingFl);
        return "";
    }

    @Override
    public Page<BookingFl> getAllBooking(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return bookingFlRepository.findAll(pageable);
    }

    @Override
    public BookingFl getBooking(long id) {
        return bookingFlRepository.findById(id).get();
    }
}
