package com.lam.javarestapi.service.impl;

import com.lam.javarestapi.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtServiceImpl implements JwtService {

    @Override
    public String generateToken(UserDetails user) {
        return "DUMMY-token";
    }
}
