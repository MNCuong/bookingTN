package com.example.booking.Repository;

import com.example.booking.Entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentTransaction, Long> {
    boolean existsByTransactionNo(String transactionNo);
}
