package com.example.shop.service;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PageService {
    List<Product> showAllProducts();
    List<Product> showConfirmedProducts();
    User showProfileById(Long id);
    Product showProductById(Long id);
}
