package com.example.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "products")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer qty;
    private String seller;

    public Product(String name, String description, Double price, Integer qty, String seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.seller = seller;
    }

    public Product(Long id, Integer qty) {
        this.id = id;
        this.qty = qty;
    }
}
