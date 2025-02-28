package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

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
    // Tham chiếu giao dịch (có thể là mã giao dịch từ cổng thanh toán)
    @Column(nullable = true)
    private String description;

}
