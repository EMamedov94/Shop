package com.example.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "carts")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer totalItems;
    private Double totalSum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cartId")
    private Set<CartItem> products = new HashSet<>();

    public Cart(Integer totalItems, Double totalSum) {
        this.totalItems = totalItems;
        this.totalSum = totalSum;
    }

//    @Transient
//    public Double getTotalSum() {
//        double sum = 0D;
//        for (CartItem product : products) {
//            sum += product.getTotalPrice();
//        }
//        return sum;
//    }
//
//    @Transient
//    @JsonIgnore
//    public Integer getTotalItems() {
//        return products.stream().map(CartItem::getQty).reduce(Integer::sum).orElse(null);
//    }
}
