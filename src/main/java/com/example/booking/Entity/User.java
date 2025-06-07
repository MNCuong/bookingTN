package com.example.booking.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String passwordHash;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String verificationToken;
    private Date tokenExpiryDate;
    private boolean verified;
    private String roles;
    private boolean active;
    private int count;
    private LocalDateTime lock_time;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserProfile userProfile;
    /**
     *
     *
     * @param flight chuyến bay
     *
     */
}