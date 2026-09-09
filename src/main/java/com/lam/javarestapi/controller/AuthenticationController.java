package com.lam.javarestapi.controller;

import com.lam.javarestapi.dto.request.SignInRequest;
import com.lam.javarestapi.dto.response.ResponseData;
import com.lam.javarestapi.dto.response.TokenResponse;
import com.lam.javarestapi.service.AuthenticationService;
import com.lam.javarestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/access")
    public ResponseEntity<TokenResponse> access(@RequestBody SignInRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public String refresh() {
        return "success refresh";
    }

    @PostMapping("/logout")
    public String logout() {
        return "success logout";
    }


}
