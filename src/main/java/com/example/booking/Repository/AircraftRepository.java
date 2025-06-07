package com.example.booking.Repository;

import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Enum.AircraftStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
//    List<Aircraft> getByAirlines_Id(Long airlinesId);
//
//    List<Aircraft> findByAirlines(Airlines airlines);

    Aircraft findByRegistration(String registration);

    boolean existsByRegistration(String registration);

    List<Aircraft> findByStatusIn(Collection<AircraftStatusEnum> statuses);
    @Query("SELECT a FROM Aircraft a WHERE a.registration = :registration")
    Aircraft getByRegistration(@Param("registration") String registration);
    @Query("""
                SELECT a FROM Aircraft a 
                WHERE (LOWER(a.registration) LIKE LOWER(CONCAT('%', :search, '%'))) 
                   OR (LOWER(a.status) LIKE LOWER(CONCAT('%', :search, '%'))) 
                   OR (LOWER(a.type) LIKE LOWER(CONCAT('%', :search, '%'))) 
            """)
    Page<Aircraft> searchAircrafts(@Param("search") String search, Pageable pageable);

}
