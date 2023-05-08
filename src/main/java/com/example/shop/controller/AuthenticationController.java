package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    // Registration new user
    @PostMapping("/registrationNewUserForm")
    public ResponseEntity<User> registrationNewUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(authenticationService.registrationNewUser(user), HttpStatus.CREATED);
    }
}