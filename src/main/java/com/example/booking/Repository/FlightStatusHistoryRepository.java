package com.example.booking.Repository;

import com.example.booking.Entity.FlightStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightStatusHistoryRepository extends JpaRepository<FlightStatusHistory, Long> {
}
