package com.example.booking.Repository;

import com.example.booking.Entity.ContactRep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactReplyRepository extends JpaRepository<ContactRep, Long> {
    Page<ContactRep> findByContact_IdOrderByCreatedAtAsc(Long contactId, Pageable pageable);
    List<ContactRep> findByContactIdOrderByCreatedAtAsc(Long ticketId);

}
