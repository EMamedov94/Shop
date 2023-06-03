package com.example.shop.service;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface UserService {
    void addProduct(Cart sessionCart, UserDetails user);
    void removeProduct(Cart sessionCart, UserDetails user);
}
