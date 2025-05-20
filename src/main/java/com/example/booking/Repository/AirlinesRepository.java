package com.example.booking.Repository;

import com.example.booking.Entity.Airlines;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlinesRepository extends JpaRepository<Airlines, Long> {
    Airlines findByCode(String code);

    boolean existsByCode(String code);

    Page<Airlines> searchAirlinesByCode(String code, Pageable pageable);
}
