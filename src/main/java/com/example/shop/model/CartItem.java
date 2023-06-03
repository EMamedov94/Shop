package com.example.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


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
    private Long cartId;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Cart cart;

    @ManyToOne(optional = false)
    private Product product;


    //    @Transient
//    public Double getTotalPrice() {
//        return getProduct().getPrice() * getQty();
//    }
}
