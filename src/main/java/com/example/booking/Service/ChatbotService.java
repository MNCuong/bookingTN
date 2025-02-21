package com.example.booking.Service;

import com.example.booking.Entity.ChatMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ChatbotService {
    List<ChatMessage> getChatHistory();
    ResponseEntity<?> askChatbox(String message, HttpServletRequest httpRequest);
    void deleteOldMessages();
}
