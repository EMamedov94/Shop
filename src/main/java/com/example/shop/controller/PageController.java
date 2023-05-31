package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.PageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class PageController {
    private final PageService pageService;

//    @GetMapping("/")
//    public ResponseEntity<List<Product>> index() {
//        List<Product> confirmedProducts = pageService.showAllProducts().stream()
//                .filter(Product::getConfirmed).collect(Collectors.toList());
//        return new ResponseEntity<>(confirmedProducts ,HttpStatus.OK);
//    }
    @GetMapping("/")
    public ResponseEntity<User> index(@AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(pageService.showAuthUser(user) ,HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> profilePage(@PathVariable Long id) {
        return new ResponseEntity<>(pageService.showProfileById(id), HttpStatus.OK);
    }
}
