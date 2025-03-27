package com.example.booking.Repository;

import com.example.booking.Entity.Airlines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlinesRepository extends JpaRepository<Airlines, Long> {
}
