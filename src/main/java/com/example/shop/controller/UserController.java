package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody ProductDto product) {
//        Product productDb = productRepository.findById(product.getId()).orElse(null);
//        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(60 * 60 * 24);
//        if (session.getAttribute("cart") == null) {
//            session.setAttribute("cart", new HashSet<>());
//        } else {
//            Set<CartItem> sessionCart = (Set<CartItem>) session.getAttribute("cart");
//            CartItem item = sessionCart.stream()
//                    .filter(f -> f.getId().equals(product.getId())).findFirst().orElse(null);
//            if (item == null) {
//                sessionCart.add(new CartItem(product.getId(), product.getQty(), productDb));
//            } else {
//                item.setQty(item.getQty() + 1);
//            }
//        }
//
//        if (userDetails != null) {
//            User user = userRepository.findByEmail(userDetails.getUsername());
//            Set<CartItem> sessionCart = (Set<CartItem>) session.getAttribute("cart");
//            sessionCart.forEach(f -> f.setCart(user.getCart()));
//            user.getCart().setProducts(sessionCart);
//            userService.addProduct(product.getId(), user.getCart());
//        }



        User user = userRepository.findByEmail(userDetails.getUsername());
        userService.addProduct(product.getId(), user.getCart());

        return new ResponseEntity<>(new ProductDto(product.getId(), product.getQty()), HttpStatus.OK);
    }

    @PostMapping("/removeProduct")
    public ResponseEntity<ProductDto> removeProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody Product product) {
        User userDb = userRepository.findByEmail(userDetails.getUsername());
        userService.removeProduct(product, userDb.getCart());
        return new ResponseEntity<>(new ProductDto(product.getId(), product.getQty()), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestBody String email) {
        return new ResponseEntity<>(userService.getUser(email), HttpStatus.OK);
    }
}
