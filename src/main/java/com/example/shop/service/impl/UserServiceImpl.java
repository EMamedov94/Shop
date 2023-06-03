package com.example.shop.service.impl;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    // Add product to cart
    @Override
    public void addProduct(Cart sessionCart, UserDetails user) {
        User userDb = userRepository.findByEmail(user.getUsername());
        Cart cartDb = userDb.getCart();

        cartDb.setProducts(sessionCart.getProducts());
        cartDb.setTotalItems(sessionCart.getTotalItems());
        cartDb.setTotalSum(sessionCart.getTotalSum());
        cartRepository.save(cartDb);
    }

    // Remove product from cart
    @Override
    public void removeProduct(Cart sessionCart, UserDetails user) {
        User userDb = userRepository.findByEmail(user.getUsername());
        Cart cart = userDb.getCart();

        cart.setProducts(sessionCart.getProducts());
        cart.setTotalItems(productService.getTotalItems(cart));
        cart.setTotalSum(productService.getTotalSum(cart));
        cartRepository.save(cart);
    }
}
