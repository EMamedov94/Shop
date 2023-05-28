package com.example.shop.service;

import com.example.shop.controller.AuthenticationResponse;
import com.example.shop.model.dto.UserDto;

public interface AuthenticationService {

    AuthenticationResponse login(UserDto user);
    AuthenticationResponse registration(UserDto user);
    boolean usedEmail(UserDto user);
}
