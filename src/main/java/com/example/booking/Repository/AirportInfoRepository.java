package com.example.booking.Repository;

import com.example.booking.Entity.AirportInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportInfoRepository extends JpaRepository<AirportInfo, Long> {
    AirportInfo findByIata(String iata);
}
