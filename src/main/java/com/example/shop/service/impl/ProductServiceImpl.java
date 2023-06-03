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

    @Override
    public Integer getTotalItems(Cart cart) {
        return cart.getProducts().stream()
                .map(CartItem::getQty)
                .reduce(Integer::sum).get();
    }

    @Override
    public Double getTotalSum(Cart cart) {
        double price = cart.getProducts().stream()
                .map(CartItem::getProduct).findFirst().get().getPrice();
        int items = cart.getProducts().stream()
                .map(CartItem::getQty)
                .reduce(Integer::sum).get();
        return price * items;
    }
}
