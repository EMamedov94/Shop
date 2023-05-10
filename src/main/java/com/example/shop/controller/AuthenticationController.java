package com.example.shop.controller;

import com.example.shop.model.dto.UserDto;
import com.example.shop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    // Registration new user
    @PostMapping("/registrationNewUserForm")
    public ResponseEntity<String> registrationNewUser(@RequestBody UserDto user) {
        authenticationService.registrationNewUser(user);
        return new ResponseEntity<>("Успешная регистрация", HttpStatus.CREATED);
    }
}