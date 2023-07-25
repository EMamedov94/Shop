package com.example.shop.service.impl;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    // Add new product to shop
    @Override
    public Product addNewProductToShop(Product product, String sellerName) {
        return productRepository.save(Product.builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .photoUrl(product.getPhotoUrl())
                        .price(product.getPrice())
                        .qty(product.getQty())
                        .category(product.getCategory())
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

    // Get total items from cart
    @Override
    public Integer getTotalItems(Cart cart) {
        return cart.getProducts().stream()
                .map(CartItem::getQty)
                .reduce(Integer::sum).orElse(0);
    }

    // Get total sum from cart
    @Override
    public Double getTotalSum(Cart cart) {
        double price = cart.getProducts().stream()
                .map(p -> p.getProduct().getPrice())
                .reduce(Double::sum).orElse(0D);
        return price * getTotalItems(cart);
    }

    // Update cart
    private void updateCart(Cart cart) {
        cart.setTotalItems(getTotalItems(cart));
        cart.setTotalSum(getTotalSum(cart));
    }

    // Add product to cart
    @Override
    public void addProduct(Cart sessionCart, UserDetails user) {
        User userDb = userRepository.findByEmail(user.getUsername());
        Cart cartDb = userDb.getCart();

        sessionCart.getProducts().forEach(f -> f.setCartId(cartDb.getId()));
        cartDb.setProducts(sessionCart.getProducts());

//        Set<CartItem> cartDbProducts = cartDb.getProducts();
//        Set<CartItem> sessionCartProducts = sessionCart.getProducts();
//
//        for (CartItem sessionItem : sessionCartProducts) {
//            boolean prodExists = cartDbProducts.stream()
//                    .anyMatch(dbItem -> dbItem.getProduct().getId().equals(sessionItem.getProduct().getId()));
//
//            if (prodExists) {
//                cartDbProducts.forEach(dbCartItem -> {
//                    if (dbCartItem.getProduct().getId().equals(sessionItem.getProduct().getId())) {
//                        dbCartItem.setQty(sessionItem.getQty());
//                    }
//                });
//            } else {
//                cartDbProducts.add(sessionItem);
//            }
//        }

        updateCart(cartDb);

        cartRepository.save(cartDb);
    }

    // Remove product from cart
    @Override
    public void removeProduct(Cart sessionCart, UserDetails user) {
        User userDb = userRepository.findByEmail(user.getUsername());
        Cart cartDb = userDb.getCart();

        sessionCart.getProducts().forEach(f -> f.setCartId(cartDb.getId()));
        cartDb.setProducts(sessionCart.getProducts());

        updateCart(cartDb);
        cartRepository.save(cartDb);
    }
}
