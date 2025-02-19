package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.BookingRequest;
import com.example.booking.DTO.Response.BookingResponse;
import com.example.booking.Entity.Booking;
import com.example.booking.Entity.Room;
import com.example.booking.Entity.User;
import com.example.booking.Enum.BookingStatusEnums;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.BookingMapper;
import com.example.booking.Repository.BookingRepository;
import com.example.booking.Service.BookingService;
import com.example.booking.Service.RoomService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.rmi.MarshalException;
import java.time.temporal.ChronoUnit;

@Slf4j
@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final MessageCommon messageCommon;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RoomService roomService;
    private final BookingMapper bookingMapper;


    @Override
    public BookingResponse booking(BookingRequest bookingRequest, HttpServletRequest httpServletRequest) {
        String token = JwtUtil.getTokenFromRequest(httpServletRequest);
        String email = jwtUtil.extractUsername(token);
        User user = userService.findUserByEmail(email);
        Room room=roomService.getRoom(bookingRequest.getRoomId());
        long overlappingBookings = bookingRepository.countOverlappingBookings(
                room.getId(), bookingRequest.getCheckIn(), bookingRequest.getCheckOut());

        if (overlappingBookings > 0) {
            throw new BookingException(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED, messageCommon.getMessage(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED));
        }
        long daysBetween = ChronoUnit.DAYS.between(bookingRequest.getCheckIn(), bookingRequest.getCheckOut());
        Double toltalPriceRaw = daysBetween * room.getPrice();
        Booking booking = Booking.builder()
                .user(user)
                .checkIn(bookingRequest.getCheckIn())
                .checkOut(bookingRequest.getCheckOut())
                .status(BookingStatusEnums.PENDING.toString())
                .totalPrice(toltalPriceRaw)
                .room(room)
                .build();
        Booking bookingSave ;
        if(booking.getStatus().equals(BookingStatusEnums.PENDING.toString())) {
            bookingSave = bookingRepository.save(booking);
        }else{
            booking.setStatus(BookingStatusEnums.FAILED.toString());
            bookingSave = bookingRepository.save(booking);

        }
        return bookingMapper.toBookingResponse(bookingSave);
    }
}
