package com.example.shop.service.impl;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Accept product to shop
    @Override
    public void acceptProductToShop(Long id) {
        Product productDb = productRepository.getReferenceById(id);
        productDb.setConfirmed(true);
        productRepository.save(productDb);
    }

    // Show all users
    @Override
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }
}
