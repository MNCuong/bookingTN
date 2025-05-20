package com.example.booking.Repository;

import com.example.booking.Entity.AirportInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportInfoRepository extends JpaRepository<AirportInfo, Long> {
    @Query("""
                SELECT a
                FROM AirportInfo a
                WHERE LOWER(a.airport) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(a.code) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(a.city) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(a.country) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(a.gate) LIKE LOWER(CONCAT('%', :search, '%'))
            """)
    Page<AirportInfo> findByAnyField(@Param("search") String search, Pageable pageable);


    boolean existsByAirport(String airport);

    boolean existsByCode(String code);

    AirportInfo findByCity(String city);

    List<AirportInfo> findAllByCity(String city);

    List<AirportInfo> findAllByCode(String code);
}
