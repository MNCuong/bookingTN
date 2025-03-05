package com.example.booking.Service.Impl;

import com.example.booking.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserCleanupService {
    private final UserService userService;
    private final RedisService verificationService;


    @Scheduled(fixedRate = 60000)  // Chạy mỗi 1 phút
    public void deleteUnverifiedUsers() {
//        List<String> unverifiedUserIds = userService.getUnverifiedUserIds();
//        for (String userId : unverifiedUserIds) {
//            if (!verificationService.isTokenValid(userId, "")) {  // Token hết hạn
//                Long idUser=Long.parseLong(userId);
//                userService.deleteById(idUser);
//            }
//        }
    }
}
