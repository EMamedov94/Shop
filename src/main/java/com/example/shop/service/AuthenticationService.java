package com.example.shop.service;

import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    User registrationNewUser(UserDto user);
}
