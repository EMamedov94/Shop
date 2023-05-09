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
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product,
                                                 @AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(productService.addNewProductToShop(product, user.getUsername()), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProductFromShop/{id}")
    public ResponseEntity<?> deleteProductFromShop(@PathVariable Long id) {
        productService.deleteProductFromShop(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
