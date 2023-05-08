package com.example.shop.controller;

import com.example.shop.components.UserDetails;
import com.example.shop.model.Product;
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

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@AuthenticationPrincipal UserDetails userDetails,
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


        //userService.addProduct(id, qty);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
