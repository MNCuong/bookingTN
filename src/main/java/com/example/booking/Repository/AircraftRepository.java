package com.example.booking.Repository;

import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<AircraftResponse> getByAirlines_Id(Long airlinesId);

    List<AircraftResponse> findByAirlines(Airlines airlines);
}
