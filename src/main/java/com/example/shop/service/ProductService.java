package com.example.shop.service;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;

public interface ProductService {
    Product addNewProductToShop(Product product, String sellerName);
    void deleteProductFromShop(Long id);
    void deleteProductFromCart(Long id, Cart cart);
    Integer getTotalItems(Cart cart);
    Double getTotalSum(Cart cart);
}
