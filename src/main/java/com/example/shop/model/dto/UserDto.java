package com.example.shop.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotEmpty(message = "email не может быть пустым")
//    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z\\d_-]+(\\\\.[A-Za-z\\d_-]+)*@[^-][A-Za-z\\d-]+(\\\\.[A-Za-z\\d-]+)*(\\\\.[A-Za-z]{2,})$",
//    message = "email не верного формата")
    @Email(message = "email не верного формата")
    private String email;
    @NotEmpty(message = "Пароль не может быть пустым")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$",
    message = "Пароль должен содержать минимум 1 заглавную букву")
    @Size(min = 8, max = 20, message = "Длина пароля должно быть от 8 до 20 символов")
    private String password;
}
