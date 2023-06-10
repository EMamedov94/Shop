package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:4200/api")
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    // Add product to cart
    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(@AuthenticationPrincipal UserDetails user,
                                                 @RequestBody ProductDto product,
                                                 HttpServletRequest request) {
        Product prod = productRepository.getProductById(product.getId());
        Cart sessionCart = (Cart) request.getSession().getAttribute("cart");

        if (sessionCart == null) {
            sessionCart = new Cart();
        }
        CartItem item = sessionCart.getProducts().stream()
                .filter(f -> f.getId().equals(product.getId())).findFirst().orElse(null);
        if (item == null) {
            sessionCart.getProducts().add(CartItem.builder()
                    .id(product.getId())
                    .qty(product.getQty())
                    .product(prod)
                    .build());
        } else {
            item.setQty(item.getQty() + 1);
        }
        sessionCart.setTotalItems(productService.getTotalItems(sessionCart));
        sessionCart.setTotalSum(productService.getTotalSum(sessionCart));
        request.getSession().setAttribute("cart", sessionCart);

        if (user != null) {
            userService.addProduct(sessionCart, user);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(product);
    }

    // Remove product from cart
    @PostMapping("/removeProduct")
    public ResponseEntity<ProductDto> removeProduct(@AuthenticationPrincipal UserDetails user,
                                                    @RequestBody ProductDto product,
                                                    HttpSession session) {
        Cart sessionCart = (Cart) session.getAttribute("cart");
        CartItem item = sessionCart.getProducts().stream()
                .filter(f -> f.getId().equals(product.getId())).findFirst().orElse(null);
        assert item != null;
        if (item.getQty() <= 1) {
            sessionCart.getProducts().remove(item);
        } else {
            item.setQty(item.getQty() - 1);
        }
        sessionCart.setTotalItems(productService.getTotalItems(sessionCart));
        sessionCart.setTotalSum(productService.getTotalSum(sessionCart));
        session.setAttribute("cart", sessionCart);

        if (user != null) {
            userService.removeProduct(sessionCart, user);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(product);
    }

    // Delete product from cart
    @DeleteMapping("/deleteProductFromCart/{id}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long id,
                                                   HttpSession request) {
        Cart sessionCart = (Cart) request.getAttribute("cart");
        productService.deleteProductFromCart(id, sessionCart);
        request.setAttribute("cart", sessionCart);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
