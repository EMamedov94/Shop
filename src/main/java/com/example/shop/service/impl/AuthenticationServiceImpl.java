package com.example.shop.service.impl;

import com.example.shop.configuration.JwtService;
import com.example.shop.controller.AuthenticationResponse;
import com.example.shop.enums.RoleEnum;
import com.example.shop.model.Cart;
import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Login
    public User login(UserDto user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        var loginUser = userRepository.findByEmail(user.getEmail());
        var jwtToken = jwtService.generateToken(loginUser);
        loginUser.setToken(jwtToken);
        return loginUser;
    }

    // Registration
    @Override
    public AuthenticationResponse registration(UserDto user) {
        var newUser = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(RoleEnum.ROLE_USER)
                .balance(0D)
                .cart(new Cart(0, 0D))
                .build();
        userRepository.save(newUser);
        var jwtToken = jwtService.generateToken(newUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // Check email exists
    @Override
    public boolean usedEmail(UserDto user) {
        return userRepository.findByEmail(user.getEmail()) != null;
    }
}
