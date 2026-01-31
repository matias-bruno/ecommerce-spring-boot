package com.techlab.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    public Role() { };
    
    public Role(String name) {
        this.name = name;
    }
}
