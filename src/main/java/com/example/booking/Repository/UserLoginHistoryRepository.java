package com.example.booking.Repository;

import com.example.booking.Entity.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {
    List<UserLoginHistory> findByUsername(String username);
}
