package com.example.shop.service;

import com.example.shop.controller.AuthenticationResponse;
import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;

public interface AuthenticationService {
    AuthenticationResponse registration(UserDto user);
    boolean usedEmail(UserDto user);
    User login(UserDto user);
}
