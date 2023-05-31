package com.example.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "cartItems")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer qty;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Product product;

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQty();
    }
}
