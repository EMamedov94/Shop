package com.example.shop.controller;

import com.example.shop.components.UserDetails;
import com.example.shop.model.Product;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class ProductController {
    private final ProductService productService;

    // Add new product to shop
    @PostMapping("/addNewProduct")
    public ResponseEntity<String> addNewProduct(@RequestBody Product product,
                                                 @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return new ResponseEntity<>("Нужна авторизация", HttpStatus.UNAUTHORIZED);
        }
        productService.addNewProductToShop(product, user.getUsername());
        return new ResponseEntity<>("Продукт добавлен", HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProductFromShop/{id}")
    public ResponseEntity<String> deleteProductFromShop(@PathVariable Long id) {
        productService.deleteProductFromShop(id);
        return new ResponseEntity<>("Продукт удален", HttpStatus.OK);
    }
}
