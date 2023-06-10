package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:4200/")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    // Registration new user
    @PostMapping("/registrationNewUser")
    public ResponseEntity<Object> registrationNewUser(@RequestBody UserDto user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (authenticationService.usedEmail(user)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email уже используется");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(authenticationService.registration(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDto user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(authenticationService.login(user));
    }
}