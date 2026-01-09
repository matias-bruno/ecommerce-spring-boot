package com.techlab.spring1.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 *
 * @author matias-bruno
 */
@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    
    @Column(nullable = false)
    private Double  price;
    
    @Column(nullable = false)
    private Integer stock;
    
    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
}
