package com.example.shop.model;

import com.example.shop.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private Double balance;
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Cart cart;

    public User(String email, String password, Double balance, RoleEnum role, Cart cart) {
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.role = role;
        this.cart = cart;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleEnum> roles = new ArrayList<>();
        roles.add(role);
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
