package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

//    @Value("${upload.path}")
//    private String uploadPath;

    // Add new product to shop
    @PostMapping("/addNewProduct")
    public ResponseEntity<Product> addNewProduct(@AuthenticationPrincipal UserDetails user,
                                                 @RequestPart Product product,
                                                 @RequestPart MultipartFile photoUrl) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (photoUrl != null) {
            File uploadDir = new File("src/main/resources/images");
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + photoUrl.getOriginalFilename();

            photoUrl.transferTo(new File(resultFileName));

            product.setPhotoUrl(resultFileName);
        }
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok()
                .headers(headers)
                .body(productService.addNewProductToShop(product, user.getUsername()));
    }


//    @PostMapping("/addNewProduct")
//    public ResponseEntity<Product> addNewProduct(@AuthenticationPrincipal UserDetails user,
//                                                 @RequestParam(name = "name") String name,
//                                                 @RequestParam(name = "description") String description,
//                                                 @RequestParam(name = "price") Double price,
//                                                 @RequestParam(name = "qty") Integer qty,
//                                                 @RequestParam(name = "photoUrl") MultipartFile photoUrl) throws IOException {
//        Product prodd = Product.builder()
//                .name(name)
//                .description(description)
//                .price(price)
//                .qty(qty)
//                .build();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        if (photoUrl != null) {
//            File uploadDir = new File("src/main/resources/images");
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFileName = uuidFile + "." + photoUrl.getOriginalFilename();
//
//            photoUrl.transferTo(new File(resultFileName));
//
//            prodd.setPhotoUrl(resultFileName);
//        }
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(productService.addNewProductToShop(prodd, user.getUsername()));
//    }

    @DeleteMapping("/deleteProductFromShop/{id}")
    public ResponseEntity<String> deleteProductFromShop(@PathVariable Long id) {
        productService.deleteProductFromShop(id);
        return new ResponseEntity<>("Продукт удален", HttpStatus.OK);
    }
}
