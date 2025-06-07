package com.example.booking.Repository;

import com.example.booking.Entity.PaymentTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTransaction, Long> {
    boolean existsByTransactionNo(String transactionNo);

    @Query("SELECT EXTRACT(MONTH FROM p.transactionDate) AS month, SUM(p.amount) " +
            "FROM PaymentTransaction p " +
            "WHERE EXTRACT(YEAR FROM p.transactionDate) = :year " +
            "GROUP BY EXTRACT(MONTH FROM p.transactionDate)")
    List<Object[]> getRevenueByMonth(@Param("year") int year);

    @Query("SELECT EXTRACT(DAY FROM p.transactionDate) AS day, SUM(p.amount) " +
            "FROM PaymentTransaction p " +
            "WHERE EXTRACT(MONTH FROM p.transactionDate) = :month AND EXTRACT(YEAR FROM p.transactionDate) = :year " +
            "GROUP BY EXTRACT(DAY FROM p.transactionDate)")
    List<Object[]> getRevenueByDayInMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT EXTRACT(MONTH FROM p.transactionDate) AS month, SUM(p.amount) " +
            "FROM PaymentTransaction p " +
            "WHERE EXTRACT(MONTH FROM p.transactionDate) BETWEEN :startMonth AND :endMonth AND EXTRACT(YEAR FROM p.transactionDate) = :year " +
            "GROUP BY EXTRACT(MONTH FROM p.transactionDate)")
    List<Object[]> getRevenueByQuarter(@Param("startMonth") int startMonth, @Param("endMonth") int endMonth, @Param("year") int year);


    Page<PaymentTransaction> findAllByTransactionNo(String transactionNo, Pageable pageable);

    PaymentTransaction findByTransactionNo(String transactionNo);
}
