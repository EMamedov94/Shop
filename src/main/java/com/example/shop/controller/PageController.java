package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
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

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:4200/api")
public class PageController {
    private final PageService pageService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> index() {
        List<Product> confirmedProducts = pageService.showAllProducts().stream()
                .filter(Product::getConfirmed).collect(Collectors.toList());
        if (confirmedProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(confirmedProducts ,HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> cart(HttpServletRequest request) {
        Cart sessionCart = (Cart) request.getSession().getAttribute("cart");
        if (sessionCart.getProducts().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(sessionCart ,HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> profilePage(@AuthenticationPrincipal UserDetails user,
                                            @PathVariable Long id) {
        return new ResponseEntity<>(pageService.showProfileById(id), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> productPage(@PathVariable Long id) {
        Product product = pageService.showProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
