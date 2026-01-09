package com.techlab.spring1.model;

import jakarta.persistence.*;

/**
 *
 * @author matias-bruno
 */

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
}
