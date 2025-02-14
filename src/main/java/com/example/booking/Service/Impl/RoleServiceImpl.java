package com.example.booking.Service.Impl;

import com.example.booking.Entity.Role;
import com.example.booking.Repository.RoleRepository;
import com.example.booking.Service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
