package com.example.booking.DTO.Response;

import com.example.booking.Entity.Airlines;
import com.example.booking.Enum.AircraftStatusEnum;
import com.example.booking.Enum.AircraftTypeEnums;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AircraftResponse {
    private Long id;
    private String registration;

    private LocalDate createAt;
    private LocalDate updateAt;
    private int seat;
    private AircraftStatusEnum status;
    private AircraftTypeEnums type;
    private String imageUrl;

}
