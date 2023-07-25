package com.example.shop.service;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;
import org.springframework.security.core.userdetails.UserDetails;

public interface ProductService {
    Product addNewProductToShop(Product product, String sellerName);
    void deleteProductFromShop(Long id);
    void deleteProductFromCart(Long id, Cart cart);
    void addProduct(Cart sessionCart, UserDetails user);
    void removeProduct(Cart sessionCart, UserDetails user);
    Integer getTotalItems(Cart cart);
    Double getTotalSum(Cart cart);
}
