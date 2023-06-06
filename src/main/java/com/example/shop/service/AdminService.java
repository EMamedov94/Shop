package com.example.shop.service;

import com.example.shop.model.User;

import java.util.List;

public interface AdminService {
    void acceptProductToShop(Long id);
    List<User> showAllUsers();
}
