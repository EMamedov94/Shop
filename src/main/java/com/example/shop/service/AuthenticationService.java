package com.example.shop.service;

import com.example.shop.model.dto.UserDto;

public interface AuthenticationService {
    Boolean registrationNewUser(UserDto user);
}
