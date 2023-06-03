package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:4200/")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    // Registration new user
    @PostMapping("/registrationNewUser")
    public ResponseEntity<AuthenticationResponse> registrationNewUser(@RequestBody UserDto user) {
        if (authenticationService.usedEmail(user)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(authenticationService.registration(user));
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDto user) {
//        return ResponseEntity.ok(authenticationService.login(user));
//    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDto user) {
        return ResponseEntity.ok(authenticationService.loginn(user));
    }
}