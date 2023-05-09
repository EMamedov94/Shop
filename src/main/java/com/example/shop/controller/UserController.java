package com.example.shop.controller;

import com.example.shop.components.UserDetails;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.AuthenticationService;
import com.example.shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Product product) {
//        HttpSession session = request.getSession();
//
//        if (session.getAttribute("cart") == null) {
//            Set<Product> cart = new HashSet<>();
//            session.setMaxInactiveInterval(60 * 60 * 24);
//            session.setAttribute("cart", cart);
//            cart.add(new Product(product.getId(), product.getQty()));
//        } else {
//            Set<Product> cart = (Set<Product>) session.getAttribute("cart");
//            Product item = cart.stream().filter(f -> f.getId() == product.getQty()).findFirst().orElse(null);
//
//            if (item == null) {
//                cart.add(new Product(product.getId(), 1));
//            } else {
//                item.setQty(item.getQty() + 1);
//            }
//        }
        User user = userRepository.findByEmail(userDetails.getUsername());

        userService.addProduct(product, user.getCart());
        return new ResponseEntity<>(new ProductDto(product.getId(), product.getQty()), HttpStatus.OK);
    }

    @PostMapping("/removeProduct")
    public ResponseEntity<ProductDto> removeProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody Product product) {
        User userDb = userRepository.findByEmail(userDetails.getUsername());
        userService.removeProduct(product, userDb.getCart());
        return new ResponseEntity<>(new ProductDto(product.getId(), product.getQty()), HttpStatus.OK);
    }
}
