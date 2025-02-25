package com.example.booking.Repository;

import com.example.booking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findUserByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    Optional<User> findByVerificationToken(String token);
    List<User> findByVerifiedFalse();
}
