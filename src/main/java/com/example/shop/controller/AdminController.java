package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.service.AdminService;
import com.example.shop.service.PageService;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adminPage")
@CrossOrigin(origins = "https://localhost:4200/")
public class AdminController {
    private final AdminService adminService;
    private final PageService pageService;
    private final ProductService productService;

    // Show all users
    @GetMapping("/showAllUsers")
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(adminService.showAllUsers(), HttpStatus.OK);
    }

    // Confirm product
    @PostMapping("/confirmProduct/{id}")
    public ResponseEntity<String> confirmProduct(@PathVariable Long id) {
        adminService.acceptProductToShop(id);
        return new ResponseEntity<>("Продукт подтвержден", HttpStatus.ACCEPTED);
    }

    // Get all products
    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(pageService.showAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteProductFromShop/{id}")
    public ResponseEntity<String> deleteProductFromShop(@PathVariable Long id) {
        productService.deleteProductFromShop(id);
        return new ResponseEntity<>("Продукт удален", HttpStatus.OK);
    }
}
