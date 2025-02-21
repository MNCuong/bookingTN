package com.example.booking.Repository;

import com.example.booking.Entity.ChatMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySender(String sender);
    @NotNull
    List<ChatMessage> findAll();

    @Modifying
    @Query("DELETE FROM ChatMessage c WHERE c.timestamp < :time")
    int deleteByTimestampBefore(LocalDateTime time);
}
