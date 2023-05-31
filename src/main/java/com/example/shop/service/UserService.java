package com.example.shop.service;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface UserService {
//    void addProduct(Long id, Cart cart);
    void addProduct(Set<CartItem> product, UserDetails user);
    void removeProduct(Product product, Cart cart);
    User getUser(String email);
}
