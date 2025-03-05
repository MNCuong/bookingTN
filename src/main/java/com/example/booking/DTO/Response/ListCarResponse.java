package com.example.booking.DTO.Response;

import com.example.booking.Enum.CarStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ListCarResponse {
    private long id;
    private String brand;
    private String model;
    private BigDecimal pricePerDay;
    private CarStatus carStatus;
    private int seatCapacity;

}
