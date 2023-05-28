package com.example.shop.service;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    void addProduct(Long id, Cart cart);
    void removeProduct(Product product, Cart cart);
    User getUser(String email);
}
