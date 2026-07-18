package com.lam.javarestapi.controller;

import com.lam.javarestapi.dto.response.ResponseData;
import com.lam.javarestapi.dto.response.ResponseError;
import com.lam.javarestapi.dto.response.ResponseSuccess;
import com.lam.javarestapi.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lam.javarestapi.dto.request.UserRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseData<Integer> addUser(@Valid @RequestBody UserRequestDTO userDTO) {
        try {
            userService.addUser(userDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(), "added User", 1);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @PutMapping("/{userId}")
    public ResponseSuccess updateUser(@PathVariable int userId, @RequestBody UserRequestDTO userDTO) {
        System.out.println("Updated user" + userId);
        return new ResponseSuccess(HttpStatus.ACCEPTED, "user pathced");
    }

    @PatchMapping("/{userId}")
    public ResponseData<?> changeStatus(@PathVariable int userId, @RequestParam(required = false) boolean status) {
        System.out.println("change user status userId=" + userId);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "user pathced");
    }

    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteUser(@Min(1) @PathVariable int userId) {
        System.out.println("deleted userid=" + userId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "user deleted");
    }

    @GetMapping("/{userId}")
    public ResponseData<UserRequestDTO> getUserDetail(@PathVariable int userId) {
        System.out.println("detail user userId=" + userId);
        return new ResponseData<>(HttpStatus.OK.value(), "got user", new UserRequestDTO("tung", "lam", "phone", "email"));

    }

    @GetMapping("/list")
    public ResponseData<List<UserRequestDTO>> getAllUser(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                         @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize) {
        System.out.println("Request get all of users");
        return new ResponseData<>(HttpStatus.OK.value(), "users", List.of(new UserRequestDTO("Tay", "Java", "admin@tayjava.vn", "0123456789"),
                new UserRequestDTO("Leo", "Messi", "leomessi@email.com", "0123456456")));
    }

}
