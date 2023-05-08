package com.example.shop.service;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.dto.ProductDto;

public interface ProductService {
    Product addNewProductToShop(ProductDto product, String sellerName);
    void deleteProductFromShop(Long id);
}
