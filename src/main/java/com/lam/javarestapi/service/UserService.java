package com.lam.javarestapi.service;

import com.lam.javarestapi.dto.request.UserRequestDTO;
import com.lam.javarestapi.dto.response.PageResponse;
import com.lam.javarestapi.dto.response.UserDetailResponse;
import com.lam.javarestapi.util.UserStatus;

public interface UserService {

    long saveUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void changeStatus(long userId, UserStatus status);

    void deleteUser(long userId);

    UserDetailResponse getUser(long userId);

    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUsersWithSortByWithMultipleColumns(int pageNo, int pageSize, String... sorts);

}
