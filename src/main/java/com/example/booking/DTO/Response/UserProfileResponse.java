package com.example.booking.DTO.Response;

import com.example.booking.Entity.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileResponse {
    private String address;
    private String gender;
    private String dateOfBirth;
    private String avatarUrl;
    private User user;
}
