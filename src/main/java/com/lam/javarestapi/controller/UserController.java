package com.lam.javarestapi.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.lam.javarestapi.dto.request.UserRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/")
    public String addUser(@Valid @RequestBody UserRequestDTO userDTO){
        return "user Added";
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable int userId,  @RequestBody UserRequestDTO userDTO){
        System.out.println("Updated user" + userId);
        return "user updated";
    }

    @PatchMapping("/{userId}")
    public String changeStatus(@PathVariable int userId, @RequestParam(required = false) boolean status){
        System.out.println("change user status userId=" + userId);
        return "user changed status";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable int userId){
        System.out.println("deleted userid=" + userId);
        return "user deleted";
    }

    @GetMapping("/{userId}")
        public UserRequestDTO getUserDetail(@PathVariable int userId){
            System.out.println("detail user userId=" + userId);
            return new UserRequestDTO("tung","lam","phone","email");

    }

    @GetMapping("/list")
    public List<UserRequestDTO> getListUsers(){
        return List.of(new UserRequestDTO("tung","lam","phone","email"),
                new UserRequestDTO("tung","lam","phone","email"));
    }

}
