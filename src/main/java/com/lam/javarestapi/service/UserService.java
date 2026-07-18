package com.lam.javarestapi.service;

import com.lam.javarestapi.dto.request.UserRequestDTO;

public interface UserService {
    int addUser(UserRequestDTO userRequestDTO);
}
