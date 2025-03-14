package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.CarRentalBookingsRequest;
import com.example.booking.Entity.CarRental;
import com.example.booking.Entity.CarRentalBooking;
import com.example.booking.Entity.User;
import com.example.booking.Enum.CarStatus;
import com.example.booking.Enum.StatusEnum;
import com.example.booking.Exception.BookingException;
import com.example.booking.Repository.CarRentalBookingsRepository;
import com.example.booking.Service.CarRentalBookingsService;
import com.example.booking.Service.CarService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import io.minio.credentials.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Service
public class CarRentalBookingsServiceImpl implements CarRentalBookingsService {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final CarRentalBookingsRepository carRentalBookingsRepository;
    private final CarService carService;
    private final MessageCommon messageCommon;

    @Transactional
    @Override
    public CarRentalBooking bookingCar(CarRentalBookingsRequest carRentalBookingsRequest, HttpServletRequest request) {
        String token = JwtUtil.getTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        User user = userService.findUserByEmail(email);
        long overlappingBookings = carRentalBookingsRepository.countOverlappingBookingCar(
                carRentalBookingsRequest.getIdCar(), carRentalBookingsRequest.getStartDate(), carRentalBookingsRequest.getEndDate());

        if (overlappingBookings > 0) {
            throw new BookingException(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED, messageCommon.getMessage(ServiceMessageConstants.THIS_TIME_HAS_BEEN_BOOKED));
        }
        long daysBetween = ChronoUnit.DAYS.between(carRentalBookingsRequest.getStartDate(), carRentalBookingsRequest.getEndDate());
        BigDecimal totalPrice = carRentalBookingsRequest.getTotalPrice().multiply(BigDecimal.valueOf(daysBetween));
        return carRentalBookingsRepository.save(CarRentalBooking.builder().user(user).car(carService.findById(carRentalBookingsRequest.getIdCar()))
                .createdAt(LocalDateTime.now())
                .endDate(carRentalBookingsRequest.getStartDate())
                .startDate(carRentalBookingsRequest.getStartDate())
                .status(StatusEnum.PENDING.toString())
                .totalPrice(totalPrice)
                .build());
    }

    @Override
    public CarRentalBooking findByBookingId(String id) {
        return carRentalBookingsRepository.findByBookingId(id);
    }

    @Override
    public void save(CarRentalBooking carRentalBooking) {
        carRentalBookingsRepository.save(carRentalBooking);
    }
}
