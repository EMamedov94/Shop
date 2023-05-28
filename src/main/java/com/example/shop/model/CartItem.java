package com.example.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer qty;

    public CartItem(Integer qty, Cart cart, Product product) {
        this.qty = qty;
        this.cart = cart;
        this.product = product;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Product product;

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQty();
    }

}
