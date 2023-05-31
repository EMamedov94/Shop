package com.example.shop.service;

import com.example.shop.model.Product;

public interface ProductService {
    Product addNewProductToShop(Product product, String sellerName);
    void deleteProductFromShop(Long id);
}
