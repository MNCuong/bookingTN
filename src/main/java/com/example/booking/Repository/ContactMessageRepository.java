package com.example.booking.Repository;

import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {
    @Query(value = """
    SELECT c FROM ContactMessage c
    WHERE c.email LIKE %:email%
    ORDER BY 
      CASE c.status 
        WHEN 'read' THEN 0
        WHEN 'new' THEN 1
        WHEN 'replied' THEN 2
        ELSE 3
      END
    """)
    Page<ContactMessage> findByEmailOrderByCustomStatus(@Param("email") String email, Pageable pageable);

    Optional<ContactMessage> findById(Long id);
}
