package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@CrossOrigin(origins = "https://localhost:4200/")
public class ProductController {
    private final ProductService productService;

    // Add new product to shop
    @PostMapping("/addNewProduct")
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product,
                                                 @AuthenticationPrincipal UserDetails user,
                                                 MultipartFile file) {
//        if (file != null) {
//
//            product.setPhoto();
//        }
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(productService.addNewProductToShop(product, user.getUsername()),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProductFromShop/{id}")
    public ResponseEntity<String> deleteProductFromShop(@PathVariable Long id) {
        productService.deleteProductFromShop(id);
        return new ResponseEntity<>("Продукт удален", HttpStatus.OK);
    }
}
