package com.lam.javarestapi.controller;

import com.lam.javarestapi.configuration.Translator;
import com.lam.javarestapi.dto.response.PageResponse;
import com.lam.javarestapi.dto.response.ResponseData;
import com.lam.javarestapi.dto.response.ResponseError;
import com.lam.javarestapi.dto.response.UserDetailResponse;
import com.lam.javarestapi.exception.ResourceNotFoundException;
import com.lam.javarestapi.service.UserService;
import com.lam.javarestapi.util.UserStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lam.javarestapi.dto.request.UserRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/")
    public ResponseData<Long> addUser(@Valid @RequestBody UserRequestDTO user) {

        try {
            long userId = userService.saveUser(user);
            return new ResponseData<>(HttpStatus.CREATED.value(), Translator.toLocale("user.add.success"), userId);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");

        }
    }


    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable long userId, @RequestBody UserRequestDTO userDTO) {
        log.info("update userid = {}", userId);
        try {
            userService.updateUser(userId, userDTO);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "user updated");
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "update user fail");
        }

    }

    @PatchMapping("/{userId}")
    public ResponseData<?> changeStatus(@PathVariable int userId, @RequestParam(required = false) UserStatus status) {
        log.info("change user status userId={}", userId);
        try {
            userService.changeStatus(userId, status);
            log.info("status changed");
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "status changed");
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "change status fail");
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteUser(@Min(1) @PathVariable int userId) {
        log.info("deleted userid={}", userId);
        try {
            userService.deleteUser(userId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "user deleted");
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "user delete fail");
        }

    }

    @GetMapping("/{userId}")
    public ResponseData<UserDetailResponse> getUserDetail(@PathVariable int userId) {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "got user", userService.getUser(userId));
        } catch (ResourceNotFoundException e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @GetMapping("/list")
    public ResponseData<PageResponse<?>> getAllUser(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                    @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                    @RequestParam(required = false) String sortBy) {

        System.out.println("Request get all of user");
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.getAllUsersWithSortBy(pageNo, pageSize, sortBy));
    }

    @GetMapping("/list-with-multiple-conlumn")
    public ResponseData<PageResponse<?>> getAllUsersWithSortByWithMultipleColumns(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                                  @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                                                  @RequestParam(required = false) String... sorts) {

        log.info("Request get all of user sort by multiple columns");
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.getAllUsersWithSortByWithMultipleColumns(pageNo, pageSize, sorts));
    }

    @GetMapping("/list-with-multiple-conlumn-search")
    public ResponseData<PageResponse<?>> getAllUsersWithSortByWithColumnAndSearch(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                                  @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                                                  @RequestParam(required = false) String search,
                                                                                  @RequestParam(required = false) String sortBy) {

        log.info("Request get all of user sort by column and search ");
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.getAllUsersWithSortByWithColumnAndSearch(pageNo, pageSize, search, sortBy));
    }

    @GetMapping("/advance-search-by-criteria")
    public ResponseData<PageResponse<?>> advanceSearchByCriteria(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                 @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                                 @RequestParam(required = false) String sortBy,
                                                                 @RequestParam(required = false) List<String> address,
                                                                 @RequestParam(required = false) String... search) {

        log.info("Request get advance search by criteria");
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.advanceSearchByCriteria(pageNo, pageSize, sortBy, address, search));
    }

}
