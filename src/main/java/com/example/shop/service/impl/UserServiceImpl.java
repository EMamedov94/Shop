package com.example.shop.service.impl;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;
import com.example.shop.repository.CartRepository;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CartRepository cartRepository;

    // Add product to cart
    @Override
    public void addProduct(Long id, Integer qty) {

    }
}
