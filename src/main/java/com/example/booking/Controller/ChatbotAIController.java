package com.example.booking.Controller;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.Entity.ChatMessage;
import com.example.booking.Service.ChatbotService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/chatbot")
public class ChatbotAIController {
    private final ChatbotService chatbotService;

    @PostMapping("/ask")
    public ResponseEntity<ResponseDto<ResponseEntity<?>>> askChatbot(@RequestParam String message, HttpServletRequest request) {
        return ResponseConfig.success(chatbotService.askChatbox(message,request));
    }
    @GetMapping("/history")
    public ResponseEntity<ResponseDto<List<ChatMessage>>> getChatHistory() {
        return ResponseConfig.success(chatbotService.getChatHistory());
    }
}
