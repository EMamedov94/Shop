package com.example.shop.service.impl;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ModeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModeratorServiceImpl implements ModeratorService {
    private final ProductRepository productRepository;

    // Accept product to shop
    @Override
    public void acceptProductToShop(Long id) {
        Product productDb = productRepository.getReferenceById(id);
        productDb.setConfirmed(true);
        productRepository.save(productDb);
    }
}
