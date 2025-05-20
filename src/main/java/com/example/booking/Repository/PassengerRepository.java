package com.example.booking.Repository;

import com.example.booking.DTO.Response.FlightResponsePackage.PassengerResponse;
import com.example.booking.Entity.Passenger;
import com.example.booking.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query("""
    SELECT p FROM Passenger p
    WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.passportNumber) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.nationalId) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.nationality) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%'))
""")
    Page<Passenger> findByAnyField(@Param("search") String search, Pageable pageable);

    List<Passenger> findByUser(User user);
}
