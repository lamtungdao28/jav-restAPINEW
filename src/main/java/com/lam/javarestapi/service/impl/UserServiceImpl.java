package com.lam.javarestapi.service.impl;

import com.lam.javarestapi.dto.request.UserRequestDTO;
import com.lam.javarestapi.exception.ResourceNotFoundException;
import com.lam.javarestapi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public int addUser(UserRequestDTO userRequestDTO) {
        System.out.println("saved user to db");
        if (!userRequestDTO.getFirstName().equals("Tay")) {
            throw new ResourceNotFoundException("tay k ton tai");
        }
        return 0;
    }
}
