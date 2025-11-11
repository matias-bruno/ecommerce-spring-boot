/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techlab.spring1.model;

/**
 *
 * @author matias-bruno
 */
public class Product {
    // Atributos privados
    private int id;
    private String nombre;
    private Double  precio;
    private Integer stock;
    
    // Constructor
    public Product(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio >= 0 ? precio : 0;
        this.stock = stock >= 0 ? stock : 0;
    }
    
    // Getters y setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio >= 0 ? precio : 0;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock >= 0 ? stock : 0;
    }
    public void agregarStock(int cantidad) {
        this.stock += cantidad;
    }
    public void descontarStock(int cantidad) {
        if(cantidad <= this.stock)
            this.stock -= cantidad;
    }
    
    @Override
    public String toString() {
        return "\nProducto Id: " + this.id + 
        " Nombre: " + this.nombre + 
        " Precio: " + String.format("$%.2f",this.precio) +
        " Stock: " + this.stock;
    }
}
