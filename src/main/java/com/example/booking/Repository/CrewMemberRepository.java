package com.example.booking.Repository;

import com.example.booking.Entity.CrewMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    Page<CrewMember> findAllByIsDelete(Boolean isDelete, Pageable pageable);

    @Query("""
                SELECT c FROM CrewMember c
                WHERE (LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')))
                   OR (LOWER(c.position) LIKE LOWER(CONCAT('%', :search, '%')))
                   OR (LOWER(c.status) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<CrewMember> searchCrewMembers(@Param("search") String search, Pageable pageable);
}
