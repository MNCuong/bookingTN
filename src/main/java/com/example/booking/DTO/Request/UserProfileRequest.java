package com.example.booking.DTO.Request;

import com.example.booking.Entity.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileRequest {
    private String address;
    private String gender;
    private String dateOfBirth;
    private String avatarUrl;
    private User user;
}
