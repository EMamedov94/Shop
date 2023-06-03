package com.example.shop.service.impl;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Show all products
    @Override
    public List<Product> showAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public User showProfileById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Product showProductById(Long id) {
        return productRepository.getProductById(id);
    }
}
