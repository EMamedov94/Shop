package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class PageController {
    private final PageService pageService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> index() {
        List<Product> confirmedProducts = pageService.showAllProducts().stream()
                .filter(Product::getConfirmed).collect(Collectors.toList());
        return new ResponseEntity<>(confirmedProducts ,HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> profilePage(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(pageService.showProfileById(id), HttpStatus.OK);
    }
}
