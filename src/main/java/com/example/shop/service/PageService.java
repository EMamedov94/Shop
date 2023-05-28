package com.example.shop.service;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PageService {
    List<Product> showAllProducts();
    User showProfileById(Long id);
    User showProfile(UserDetails user);
}
