package com.example.booking.Repository;

import com.example.booking.Entity.CodeSharedFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeSharedFlightRepository extends JpaRepository<CodeSharedFlight, Long> {
}
