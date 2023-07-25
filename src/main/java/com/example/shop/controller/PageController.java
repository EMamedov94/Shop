package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.User;
import com.example.shop.service.PageService;
import com.example.shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:4200")
public class PageController {
    private final PageService pageService;
    private final UserService userService;


    // Show confirmed products
    @GetMapping("/")
    public ResponseEntity<Object> index() {

        if (pageService.showConfirmedProducts() == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Продукты не найдены");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageService.showConfirmedProducts());
    }

    // Get session cart
    @GetMapping("/cart")
    public ResponseEntity<Object> cart(@AuthenticationPrincipal UserDetails user,
                                       HttpSession session) {

        Cart sessionCart = (Cart) session.getAttribute("cart");

        if (user != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.getUserCart(user, sessionCart));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Objects.requireNonNullElse(sessionCart, "Ваша корзина пока пуста"));
    }

    // Get profile by id
    @GetMapping("/profile/{id}")
    public ResponseEntity<User> profilePage(@AuthenticationPrincipal UserDetails user,
                                            @PathVariable Long id) {
        return new ResponseEntity<>(pageService.showProfileById(id), HttpStatus.OK);
    }

    // Get product by id
    @GetMapping("/product/{id}")
    public ResponseEntity<Object> productPage(@PathVariable Long id) {
        if (pageService.showProductById(id) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Продукт не найден");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageService.showProductById(id));
    }
}
