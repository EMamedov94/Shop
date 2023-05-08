package com.example.shop.service.impl;

import com.example.shop.components.UserDetails;
import com.example.shop.enums.RoleEnum;
import com.example.shop.model.Cart;
import com.example.shop.model.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Login user
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userDb = userRepository.findByEmail(email);
        if (userDb == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserDetails(userDb);
    }

    // Registration new user
    @Override
    public User registrationNewUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(new User(
                user.getEmail(),
                user.getPassword(),
                0D,
                RoleEnum.ROLE_USER,
                new Cart()
        ));
    }
}
