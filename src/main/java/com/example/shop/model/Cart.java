package com.example.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "carts")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer totalItems;
    private Double totalSum;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<CartItem> products = new HashSet<>();

    @Transient
    public Double totalSum() {
        double sum = 0D;
        for (CartItem product : products) {
            sum += product.getTotalPrice();
        }
        return sum;
    }
    @Transient
    public int getNumberOfProducts() {
        return this.products.size();
    }
}
