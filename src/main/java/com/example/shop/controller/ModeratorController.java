package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.service.ModeratorService;
import com.example.shop.service.PageService;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/moderator")
public class ModeratorController {
    private final ModeratorService moderatorService;
    private final PageService pageService;

    // Confirm product
    @PostMapping("/confirmProduct/{id}")
    public ResponseEntity<String> confirmProduct(@PathVariable Long id) {
        moderatorService.acceptProductToShop(id);
        return new ResponseEntity<>("Продукт подтвержден", HttpStatus.ACCEPTED);
    }

    // Get all products
    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(pageService.showAllProducts(), HttpStatus.OK);
    }
}
