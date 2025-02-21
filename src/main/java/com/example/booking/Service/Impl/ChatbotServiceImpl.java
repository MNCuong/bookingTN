package com.example.booking.Service.Impl;

import com.example.booking.Entity.ChatMessage;
import com.example.booking.Entity.User;
import com.example.booking.Repository.ChatMessageRepository;
import com.example.booking.Service.ChatbotService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ChatbotServiceImpl implements ChatbotService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ChatMessageRepository chatMessageRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public List<ChatMessage> getChatHistory() {
        return chatMessageRepository.findAll();
    }

    @Override
    public ResponseEntity<?> askChatbox(String message, HttpServletRequest httpRequest) {
        log.info("📩 User gửi tin nhắn: {}", message);
        ChatMessage userMessage = new ChatMessage();
        ResponseEntity<Object[]> response = null;
        User user = null;
        String token = JwtUtil.getTokenFromRequest(httpRequest);
        if (token != null) {
            String email = jwtUtil.extractUsername(token);
            user = userService.findUserByEmail(email);
            userMessage.setSender(email);
            userMessage.setMessage(message);
            chatMessageRepository.save(userMessage);
        }


        Map<String, String> request = Map.of("sender", user == null ? "user" : user.getFullName(), "message", message);

        try {
            String RASA_URL = "http://localhost:5005/webhooks/rest/webhook";
            response = restTemplate.postForEntity(RASA_URL, request, Object[].class);
            log.info("✅ Bot phản hồi: {}", response.getBody());

            if (response.getBody() != null && response.getBody().length > 0) {
                for (Object obj : response.getBody()) {
                    Map<?, ?> responseMap = (Map<?, ?>) obj;
                    String botMessage = (String) responseMap.get("text");
                    ChatMessage botResponse = new ChatMessage();
                    botResponse.setSender("bot");
                    botResponse.setMessage(botMessage);
                    chatMessageRepository.save(botResponse);
                }
            }

        } catch (Exception e) {
            log.error("❌ Lỗi khi gọi Rasa: ", e);
            e.printStackTrace();
        }
        return ResponseEntity.ok(response.getBody());
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")  // Chạy mỗi ngày lúc 00:00
    public void deleteOldMessages() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        int deletedRows = chatMessageRepository.deleteByTimestampBefore(oneDayAgo);
        log.info("🗑 Đã xóa {} tin nhắn cũ trước {}", deletedRows, oneDayAgo);
    }
}
