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
    private String photoUrl;
    private String description;
    private Double price;
    private Integer qty;
    private String category;
    private String seller;
    private Boolean confirmed;
}
