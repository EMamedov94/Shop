package com.example.shop.service.impl;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    // Add new product to shop
    @Override
    public Boolean addNewProductToShop(Product product, String sellerName) {
        productRepository.save(new Product(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQty(),
                sellerName,
                false
        ));
        return true;
    }

    // Delete product from shop
    @Override
    public void deleteProductFromShop(Long id) {
        productRepository.delete(productRepository.getReferenceById(id));
    }
}
