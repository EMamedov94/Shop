package com.example.shop.service.impl;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // Add product to cart
    @Override
    public void addProduct(Product product, Cart cart) {
        Product productDb = productRepository.getReferenceById(product.getId());
        CartItem cartItem = cart.getProducts().stream()
                        .filter(f -> f.getProduct().getId().equals(productDb.getId())).findFirst()
                        .orElse(null);
        if (cartItem != null) {
            cartItem.setQty(cartItem.getQty() + 1);
        } else {
            cart.getProducts().add(new CartItem(1, cart, product));
        }
        cart.setTotalItems(cart.getTotalItems() + 1);
        cart.setTotalSum(cart.totalSum());
        cartRepository.save(cart);
    }

    // Remove product from cart
    @Override
    public void removeProduct(Product product, Cart cart) {
        Product productDb = productRepository.getReferenceById(product.getId());
        CartItem cartItem = cart.getProducts().stream()
                .filter(f -> f.getProduct().getId().equals(productDb.getId())).findFirst()
                .orElse(null);
        assert cartItem != null;
        if (cartItem.getQty() <= 1) {
            cart.getProducts().remove(cartItem);
        } else {
            cartItem.setQty(cartItem.getQty() - 1);
        }
        cart.setTotalItems(cart.getTotalItems() - 1);
        cart.setTotalSum(cart.totalSum());
        cartRepository.save(cart);
    }
}
