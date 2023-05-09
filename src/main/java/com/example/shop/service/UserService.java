package com.example.shop.service;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;

public interface UserService {
    void addProduct(Product product, Cart cart);
    void removeProduct(Product product, Cart cart);
}
