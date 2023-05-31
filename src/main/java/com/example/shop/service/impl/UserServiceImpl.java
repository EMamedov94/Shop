package com.example.shop.service.impl;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Add product to cart
//    @Override
//    public void addProduct(Long id, Cart cart) {
//        Product productDb = productRepository.getReferenceById(id);
//        CartItem cartItem = cart.getProducts().stream()
//                        .filter(f -> f.getProduct().getId().equals(productDb.getId())).findFirst()
//                        .orElse(null);
//        if (cartItem != null) {
//            cartItem.setQty(cartItem.getQty() + 1);
//        } else {
//            cart.getProducts().add(new CartItem(1, cart, productDb));
//        }
//        cart.setTotalItems(cart.getTotalItems() + 1);
//        cart.setTotalSum(cart.getTotalSum());
//        cartRepository.save(cart);
//    }

//    @Override
//    public void addProduct(ProductDto product, UserDetails user) {
//        User userDb = userRepository.findByEmail(user.getUsername());
//        Cart cart = userDb.getCart();
//        Product productDb = productRepository.getReferenceById(product.getId());
//
//        CartItem cartItem = cart.getProducts().stream()
//                .filter(f -> f.getProduct().getId().equals(productDb.getId())).findFirst()
//                .orElse(null);
//        if (cartItem != null) {
//            cartItem.setQty(cartItem.getQty() + 1);
//        } else {
//            cart.getProducts().add(new CartItem(1, cart, productDb));
//        }
//        cart.setTotalItems(cart.getTotalItems() + 1);
//        cart.setTotalSum(cart.getTotalSum());
//        cartRepository.save(cart);
//    }

    @Override
    public void addProduct(Set<CartItem> product, UserDetails user) {
        User userDb = userRepository.findByEmail(user.getUsername());
        Cart cart = userDb.getCart();
        product.forEach(f -> f.setCart(cart));
        cart.setProducts(product);

        cart.setTotalItems(cart.getTotalItems());
        cart.setTotalSum(cart.getTotalSum());
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
        cart.setTotalSum(cart.getTotalSum());
        cartRepository.save(cart);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }
}
