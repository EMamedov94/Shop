package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@CrossOrigin(origins = "https://localhost:4200/")
public class ProductController {
    private final ProductService productService;

    // Add new product to shop
    @PostMapping("/addNewProduct")
    public ResponseEntity<Object> addNewProduct(@AuthenticationPrincipal UserDetails user,
                                                 @RequestPart Product product,
                                                 @RequestPart MultipartFile photoUrl) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (photoUrl != null) {
            File uploadDir = new File("D:\\java\\Shop-front\\src\\assets\\images\\products"
                    + "\\" + user.getUsername());
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + photoUrl.getOriginalFilename();
            photoUrl.transferTo(new File(uploadDir + "\\" + resultFileName));

            product.setPhotoUrl("/assets/images/products/" + user.getUsername() + "/" + resultFileName);
        }
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Нужна авторизация");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(productService.addNewProductToShop(product, user.getUsername()));
    }

    // Delete product from shop
    @DeleteMapping("/deleteProductFromShop/{id}")
    public ResponseEntity<String> deleteProductFromShop(@PathVariable Long id) {
        productService.deleteProductFromShop(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Продукт удален");
    }
}
