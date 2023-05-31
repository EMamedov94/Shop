package com.example.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "carts")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer totalItems;
    private Double totalSum;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartItem> products = new HashSet<>();

    public Cart(Integer totalItems, Double totalSum) {
        this.totalItems = totalItems;
        this.totalSum = totalSum;
    }

    @Transient
    public Double getTotalSum() {
        double sum = 0D;
        for (CartItem product : products) {
            sum += product.getTotalPrice();
        }
        return sum;
    }

    @Transient
    public Integer getTotalItems() {
        return products.stream().map(CartItem::getQty).reduce(Integer::sum).get();
    }
}
