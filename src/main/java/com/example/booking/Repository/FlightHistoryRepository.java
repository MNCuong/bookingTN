package com.example.booking.Repository;

import com.example.booking.Entity.FlightHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightHistoryRepository extends JpaRepository<FlightHistory, Long> {
}
