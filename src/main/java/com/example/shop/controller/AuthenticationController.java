package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:4200")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    // Registration new user
    @PostMapping("/registrationNewUser")
    public ResponseEntity<Object> registrationNewUser(@Valid @RequestBody UserDto user,
                                                      BindingResult result) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (result.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result.getAllErrors());
        }

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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            SecurityContextHolder.clearContext();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Logout success");
    }
}