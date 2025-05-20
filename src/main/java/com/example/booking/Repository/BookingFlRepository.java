package com.example.booking.Repository;

import com.example.booking.Entity.BookingFl;
import com.example.booking.Entity.FlightBooking;
import com.example.booking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingFlRepository extends JpaRepository<BookingFl, Long> {

    @Query("SELECT b FROM BookingFl b WHERE b.user = :user ORDER BY b.createdAt ASC")
    List<BookingFl> findByUserOrderByCreateAtAsc(User user);
}

