package com.example.booking.Repository;

import com.example.booking.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findUserByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByFullName(String full_name);

    Boolean existsByPhone(String phone);

    Optional<User> findByVerificationToken(String token);

    List<User> findByVerifiedFalse();

    @Query("""
                SELECT u FROM User u
                WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(u.roles) LIKE LOWER(CONCAT('%', :search, '%'))
            """)
    Page<User> findAllAnyField(@Param("search") String search, Pageable pageable);

    User findUserById(Long id);
}
