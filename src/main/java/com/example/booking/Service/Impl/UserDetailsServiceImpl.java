package com.example.booking.Service.Impl;

import com.example.booking.Entity.User;
import com.example.booking.Exception.BookingException;
import com.example.booking.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
//    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        if (!user.isActive()) {
            if (unlockWhenTimeExpired(user)) {
                log.info("✅ Tài khoản đã được mở khóa sau khi hết thời gian khóa.");
            } else {
                throw new LockedException("Tài khoản của bạn đang bị khóa. Vui lòng thử lại sau");
            }
        }

        return new UserDetailsImpl(user);
    }

    public boolean unlockWhenTimeExpired(User user) {
        LocalDateTime lockTime = user.getLock_time();
        if (lockTime != null) {
            LocalDateTime unlockTime = lockTime.plusMinutes(15);
            if (LocalDateTime.now().isAfter(unlockTime)) {
                user.setActive(true);
                user.setLock_time(null);
                user.setCount(0);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

}

