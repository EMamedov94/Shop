package com.example.shop.service;

import com.example.shop.model.Product;

public interface ProductService {
    Boolean addNewProductToShop(Product product, String sellerName);
    void deleteProductFromShop(Long id);
}
