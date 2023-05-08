package com.example.shop.service;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;

public interface UserService {
    void addProduct(Long id, Integer qty);
}
