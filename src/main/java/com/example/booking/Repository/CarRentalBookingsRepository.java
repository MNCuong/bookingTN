package com.example.booking.Repository;

import com.example.booking.Entity.CarRentalBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CarRentalBookingsRepository extends JpaRepository<CarRentalBooking,Long> {
    @Query("SELECT COUNT(b) FROM CarRentalBooking b WHERE b.car.id = :carId " +
            "AND (:startDate BETWEEN b.startDate AND b.endDate OR " +
            ":endDate BETWEEN b.startDate AND b.endDate OR " +
            "(b.startDate BETWEEN :startDate AND :endDate))")
    long countOverlappingBookingCar(@Param("carId") long carId,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

    CarRentalBooking findByBookingId(String id);
}
