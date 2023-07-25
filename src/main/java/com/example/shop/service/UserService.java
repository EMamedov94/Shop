package com.example.shop.service;

import com.example.shop.model.Cart;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    Cart getUserCart(UserDetails user, Cart sessionCart);
}
