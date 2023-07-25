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
@CrossOrigin(origins = "https://localhost:4200")
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;


    // Add product to cart
    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ProductDto product,
            HttpSession session
    ) {
        // Получить продукт из репозитория по идентификатору
        Product prod = productRepository.getProductById(product.getId());

        // Получить корзину из сессии
        Cart sessionCart = (Cart) session.getAttribute("cart");

        // Если корзины нет, создать новую
        if (sessionCart == null) {
            sessionCart = new Cart();
        }

        // Проверить, есть ли товар уже в корзине
        CartItem item = sessionCart.getProducts().stream()
                .filter(f -> f.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        // Если товар не найден, добавить его в корзину
        if (item == null) {
            sessionCart.getProducts().add(CartItem.builder()
                    .qty(product.getQty())
                    .product(prod)
                    .build());
        } else {
            // Если товар уже есть, увеличить количество на 1
            item.setQty(item.getQty() + 1);
        }

        // Обновить общее количество товаров и сумму в корзине
        sessionCart.setTotalItems(productService.getTotalItems(sessionCart));
        sessionCart.setTotalSum(productService.getTotalSum(sessionCart));

        // Сохранить корзину в сессии
        session.setAttribute("cart", sessionCart);

        // Если пользователь аутентифицирован, вызвать метод сервиса
        if (user != null) {
            productService.addProduct(sessionCart, user);
        }

        // Вернуть ответ с HTTP статусом 200 и телом продукта
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(product);
    }

    // Remove product from cart
    @PostMapping("/removeProduct")
    public ResponseEntity<ProductDto> removeProduct(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ProductDto product,
            HttpSession session
    ) {
        // Получение корзины из сессии
        Cart sessionCart = (Cart) session.getAttribute("cart");

        // Поиск товара в корзине по идентификатору
        CartItem item = sessionCart.getProducts().stream()
                .filter(f -> f.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        // Удаление товара из корзины или уменьшение количества
        assert item != null;
        if (item.getQty() <= 1) {
            sessionCart.getProducts().remove(item);
        } else {
            item.setQty(item.getQty() - 1);
        }

        // Обновление общего количества товаров и суммы в корзине
        sessionCart.setTotalItems(productService.getTotalItems(sessionCart));
        sessionCart.setTotalSum(productService.getTotalSum(sessionCart));

        // Обновление корзины в сессии
        session.setAttribute("cart", sessionCart);

        // Если пользователь авторизован, удаление товара из базы данных
        if (user != null) {
            productService.removeProduct(sessionCart, user);
        }

        // Возвращение ответа с кодом 200 OK и удаленным товаром
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
