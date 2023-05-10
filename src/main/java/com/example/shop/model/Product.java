package com.example.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "products")
@Entity
@NoArgsConstructor
@Getter
@Setter
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer qty;
    private String seller;
    private Boolean confirmed;

    public Product(String name,
                   String description,
                   Double price,
                   Integer qty,
                   String seller,
                   Boolean confirmed) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.seller = seller;
        this.confirmed = confirmed;
    }
}
