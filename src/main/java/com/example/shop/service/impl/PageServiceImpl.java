package com.example.shop.service.impl;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {
    private final ProductRepository productRepository;

    // Show all products
    @Override
    public List<Product> showAllProducts() {
        return productRepository.findAll();
    }
}
