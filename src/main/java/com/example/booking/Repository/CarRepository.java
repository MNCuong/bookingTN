package com.example.booking.Repository;

import com.example.booking.DTO.Response.ListCarResponse;
import com.example.booking.Entity.CarRental;
import com.example.booking.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarRental, Long>{
    Optional<CarRental> findById(Long id);

    List<CarRental> findByHotel(Hotel hotel);
}
