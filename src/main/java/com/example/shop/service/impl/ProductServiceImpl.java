package com.example.shop.service.impl;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
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
    public Product addNewProductToShop(Product product, String sellerName) {
        return productRepository.save(Product.builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .qty(product.getQty())
                        .seller(sellerName)
                        .confirmed(false)
                        .build());
    }

    // Delete product from shop
    @Override
    public void deleteProductFromShop(Long id) {
        productRepository.delete(productRepository.getReferenceById(id));
    }

    // Delete product from cart
    @Override
    public void deleteProductFromCart(Long id, Cart cart) {
        cart.getProducts().removeIf(f -> f.getProduct().getId().equals(id));
        cart.setTotalSum(getTotalSum(cart));
        cart.setTotalItems(getTotalItems(cart));
    }

    @Override
    public Integer getTotalItems(Cart cart) {
        return cart.getProducts().stream()
                .map(CartItem::getQty)
                .reduce(Integer::sum).orElse(0);
    }

    @Override
    public Double getTotalSum(Cart cart) {
        double price = cart.getProducts().stream()
                .map(p -> p.getProduct().getPrice())
                .reduce(Double::sum).orElse(0D);
        return price * getTotalItems(cart);
    }
}
