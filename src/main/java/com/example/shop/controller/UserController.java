package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.model.dto.UserDto;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(@AuthenticationPrincipal UserDetails user,
                                                 HttpSession session,
                                                 @RequestBody ProductDto product) {
        Product prod = productRepository.getProductById(product.getId());
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new HashSet<>());
        }
        Set<CartItem> sessionCart = (Set<CartItem>) session.getAttribute("cart");
        CartItem item = sessionCart.stream()
                .filter(f -> f.getId().equals(product.getId())).findFirst().orElse(null);
        if (item == null) {
            sessionCart.add(CartItem.builder()
                    .id(product.getId())
                    .qty(product.getQty())
                    .product(prod)
                    .build());
            session.setAttribute("cart", sessionCart);
        } else {
            item.setQty(item.getQty() + 1);
            session.setAttribute("cart", sessionCart);
        }

        if (user != null) {
            userService.addProduct(sessionCart, user);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/removeProduct")
    public ResponseEntity<ProductDto> removeProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody Product product) {
        User userDb = userRepository.findByEmail(userDetails.getUsername());
        userService.removeProduct(product, userDb.getCart());
        return new ResponseEntity<>(new ProductDto(product.getId(), product.getQty()), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.getUser(user.getEmail()));
    }
}
