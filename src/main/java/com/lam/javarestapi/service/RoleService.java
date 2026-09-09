package com.lam.javarestapi.service;

import com.lam.javarestapi.model.Role;
import com.lam.javarestapi.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record RoleService(RoleRepository roleRepository) {

    @PostConstruct
    public List<Role> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles;
    }

}
