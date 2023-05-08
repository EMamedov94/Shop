package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PageController {
    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<User>> index() {
        return new ResponseEntity<>(userRepository.findAll() ,HttpStatus.OK);
    }
}
