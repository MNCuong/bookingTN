package com.example.booking.Entity;

import com.example.booking.Enum.TypeServiceEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "payment_transaction")
@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = true)
    private String paymentMethod;

    @Column(nullable = true)
    private String transactionNo;
    @Column(nullable = true)
    private String description;
    @OneToOne
    private Ticket ticket;
    // Quan hệ với User (Người thanh toán)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(nullable = true)
    private String typeBooking;

    @Column(nullable = true)
    private TypeServiceEnum typeService;
    @OneToMany(mappedBy = "paymentTransaction", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
