package com.example.shop.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private String email;
    private String password;
}
