package com.example.booking.Entity;

import com.example.booking.Enum.AircraftStatusEnum;
import com.example.booking.Enum.AircraftTypeEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aircraft")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registration;
    private String iata;
    private String icao;
    private String icao24;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AircraftStatusEnum status;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AircraftTypeEnums type;
    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airlines airlines;
}
