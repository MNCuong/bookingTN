package com.example.booking.Entity;

import com.example.booking.Enum.AircraftStatusEnum;
import com.example.booking.Enum.AircraftTypeEnums;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AircraftStatusEnum status;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AircraftTypeEnums type;
    @JsonProperty("seatCapacity")
    public int getSeatCapacity() {
        return type != null ? type.getSeatCapacity() : 0;
    }
    @Column(name = "seat", nullable = false)
    private int seat;
    @Column(columnDefinition = "TEXT")
    private String imgUrl;
    private LocalDate createAt;
    private LocalDate updateAt;
    @OneToMany(mappedBy = "aircraft")
    private List<Flight> flights;
}
