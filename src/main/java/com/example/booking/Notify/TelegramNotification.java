package com.example.booking.Notify;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramNotification {
    public void sendTelegramNotification(String message) {
        String chatId = "7021971816";
        String botToken = "8026667477:AAGtYvcarvaNWxyJK8Gtgitn21IPGgTRltE";
        String telegramApiUrl = "https://api.telegram.org/bot" + botToken + "/sendMessage?chat_id=" + chatId + "&text=" + message;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(telegramApiUrl, String.class);
    }
}
