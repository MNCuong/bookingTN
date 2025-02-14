package com.example.booking.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String email;
    private String password;
    private String full_name;
    private String phone_number;
}
