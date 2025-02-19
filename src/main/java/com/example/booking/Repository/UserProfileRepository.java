package com.example.booking.Repository;

import com.example.booking.Entity.User;
import com.example.booking.Entity.UserProfile;
import com.example.booking.Service.UserProfileService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUser(User user);
    UserProfile findByUser_Id(Long user_id);

}
