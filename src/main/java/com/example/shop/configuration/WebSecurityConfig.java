package com.example.shop.configuration;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/",
                            "/registrationNewUser",
                            "/login",
                            "/addProduct",
                            "/removeProduct",
                            "deleteProductFromCart/**",
                            "/cart",
                            "product/**",
                            "/profile/**").permitAll()
//                    .requestMatchers("/profile/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .requestMatchers("/adminPage/**").hasAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated()
//                .and()
//                .formLogin()
//                    .loginPage("/")
//                    .permitAll()
//                .and()
//                .logout()
//                    .logoutSuccessUrl("/")
//                    .deleteCookies("JSESSIONID")
//                    .invalidateHttpSession(true)
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
