package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String passportNumber;

    private String nationalId;

    private String nationality;
    private String email;
    private LocalDate birthDate;
    private boolean gender;
    private BigDecimal price;
    private String type;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "passenger")
    private List<Ticket> tickets;

    public boolean isInternational() {
        return !this.nationality.equals("Viet Nam");
    }

    public String getIdentification() {
        if (isInternational()) {
            return passportNumber;
        } else {
            return nationalId;
        }
    }
}
