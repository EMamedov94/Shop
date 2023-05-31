package com.example.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "products")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer qty;
    private String seller;
    private Boolean confirmed;

//    public Product(String name,
//                   String description,
//                   Double price,
//                   Integer qty,
//                   String seller,
//                   Boolean confirmed) {
//        this.name = name;
//        this.description = description;
//        this.price = price;
//        this.qty = qty;
//        this.seller = seller;
//        this.confirmed = confirmed;
//    }

//    public Product(Long id, Integer qty) {
//        this.id = id;
//        this.qty = qty;
//    }
}
