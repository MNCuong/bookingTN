package com.example.booking.Repository;

import com.example.booking.DTO.Response.FlightResponsePackage.TicketResponse;
import com.example.booking.Entity.Flight;
import com.example.booking.Entity.Passenger;
import com.example.booking.Entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByPassenger_Id(Long passengerId);

    List<Ticket> getTicketsByPassenger_Id(Long passengerId);

    @Query("""
                SELECT t FROM Ticket t 
                LEFT JOIN t.passenger p 
                LEFT JOIN t.flight f 
                LEFT JOIN t.paymentTransaction pay 
                WHERE LOWER(t.seatNumber) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(t.classType) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(p.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(f.flightCode) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(pay.transactionNo) LIKE LOWER(CONCAT('%', :search, '%'))
            """)
    Page<Ticket> searchTickets(String search, Pageable pageable);

    List<Ticket> findByFlight(Flight flight);

    List<Ticket> findByFlight_Id(Long flightId);

    @Query("SELECT t FROM Ticket t WHERE t.passenger IN :passengers")
    Page<Ticket> findAllByPassengers(@Param("passengers") List<Passenger> passengers, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.paymentTransaction.user.id = :userId")
    List<Ticket> findTicketsByUserId(@Param("userId") Long userId);

}

