package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position; // PILOT, COPILOT, ATTENDANT

    private LocalDate licenseExpiry;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;
    @ManyToMany(mappedBy = "crew")
    private List<Flight> assignedFlights;
    private String status;
    private Boolean isDelete;
}
