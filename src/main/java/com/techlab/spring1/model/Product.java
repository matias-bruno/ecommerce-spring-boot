/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techlab.spring1.model;

import jakarta.persistence.*;

/**
 *
 * @author matias-bruno
 */
@Entity
@Table(name="productos")
public class Product {
    // Atributos privados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double  precio;
    private Integer stock;
    
    // Constructores
    public Product() { };
    
    public Product(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio >= 0 ? precio : 0;
        this.stock = stock >= 0 ? stock : 0;
    }
    
    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    
    @Override
    public String toString() {
        return "\nProducto Id: " + this.id + 
        " Nombre: " + this.nombre + 
        " Precio: " + String.format("$%.2f",this.precio) +
        " Stock: " + this.stock;
    }
}
