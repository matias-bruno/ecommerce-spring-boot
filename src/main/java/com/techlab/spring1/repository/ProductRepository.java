/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techlab.spring1.repository;

import com.techlab.spring1.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matias-bruno
 */
@Repository
public class ProductRepository {

    // El arreglo con los productos
    private final ArrayList<Product> productos;
    // El contador estático para los ids
    private static int nextId = 1;

    // Constructor
    public ProductRepository() {
        this.productos = new ArrayList<>();
        this.addProductos();
    }

    public List<Product> getProducts() {
        return this.productos;
    }

    // Metodo para tener productos ingresados
    private void addProductos() {
        this.saveProduct(new Product("Cafe Morenita", 5000, 5));
        this.saveProduct(new Product("Te Virginia", 1000, 10));
        this.saveProduct(new Product("Leche Serenisima", 1500, 10));
        this.saveProduct(new Product("Lavandina Ayudin", 1000, 10));
        this.saveProduct(new Product("Desodorante Axe", 2000, 10));
    }

    public Product saveProduct(Product newProduct) {
        this.updateId(newProduct);
        productos.add(newProduct);
        return newProduct;
    }

    public Product getProductById(int id) {
        for (Product producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }

    public Product getProductByName(String nombre) {
        for (Product producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null;
    }

    public void deleteProduct(Product producto) {
        productos.remove(producto);
    }

    private void updateId(Product producto) {
        producto.setId(nextId++);
    }
}
