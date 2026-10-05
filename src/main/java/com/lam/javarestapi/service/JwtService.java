package com.lam.javarestapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtService {
    String generateToken(UserDetails user);

    String generateRefreshToken(UserDetails user);

    String extractUsername(String token);

    boolean isValid(String token, UserDetails user);
}
