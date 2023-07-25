package com.example.shop.service.impl;

import com.example.shop.model.Cart;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    // Get user cart
    @Override
    public Cart getUserCart(UserDetails user, Cart sessionCart) {
        return userRepository.findByEmail(user.getUsername()).getCart();
    }
}
