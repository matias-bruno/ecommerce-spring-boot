/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techlab.spring1.service;

import com.techlab.spring1.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author matias-bruno
 */

@Service
public class ProductService {
    // El arreglo con los productos
    private final ArrayList<Product> productos;
    
    // Constructor
    public ProductService() {
        this.productos = new ArrayList<>();
        this.agregarProductos();
    }
    
    public List<Product> getProducts() {
        return this.productos;
    }
    
    // Metodo para tener productos ingresados
    public void agregarProductos() {
        productos.add(new Product("Cafe Morenita", 5000, 5));
        productos.add(new Product("Te Virginia", 1000, 10));
        productos.add(new Product("Leche Serenisima", 1500, 10));
        productos.add(new Product("Lavandina Ayudin", 1000, 10));
        productos.add(new Product("Desodorante Axe", 2000, 10));
    }
    public boolean saveProduct(Product newProduct) {
        newProduct.setNombre(formatearNombre(newProduct.getNombre()));
        if(productoValido(newProduct.getNombre(), newProduct.getPrecio(), newProduct.getStock())) {
            productos.add(newProduct);
            System.out.println("Producto ingresado");
            return true;
        } else {
            System.out.println("No se pudo ingresar el producto porque contenia datos no validos");
        }
        return false;
    }
    public boolean updateProduct(Product producto, Product newProduct) {
        String nombreProducto = newProduct.getNombre();
        double precioProducto = newProduct.getPrecio();
        int cantidadEnStock = newProduct.getStock();
        if(productoValido(nombreProducto, precioProducto, cantidadEnStock)) {
            producto.setNombre(nombreProducto);
            producto.setPrecio(precioProducto);
            producto.setStock(cantidadEnStock);
            System.out.println("Producto actualizado");
            return true;
        } else {
            System.out.println("No se pudo actualizar el producto porque contenia datos no validos");
        }
        return false;
    }
    public Product getProductById(int id) {
        for(Product producto : productos) {
            if(producto.getId() == id)
                return producto;
        }
        return null;
    }
    public Product buscarProductoPorNombre(String nombre) {
        nombre = this.formatearNombre(nombre);
        for(Product producto : productos) {
            if(producto.getNombre().equalsIgnoreCase(nombre))
                return producto;
        }
        return null;
    }
    public void deleteProduct(Product producto) {
        productos.remove(producto);
        System.out.println("Producto eliminado");
    }
    // Metodo auxiliar para nombres de productos
    public String formatearNombre(String nombre) {
        String[] palabras = nombre.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String palabra : palabras) {
            sb.append(palabra.substring(0,1).toUpperCase());
            sb.append(palabra.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
    public boolean productoValido(String nombre, double precio, int cantidad) {
        boolean valido = true;
        if(nombre.length() < 3) {
            System.out.println("El nombre debe contener al menos 3 letras");
            valido = false;
        }
        if(precio <= 0) {
            System.out.println("El precio debe ser un numero positivo");
            valido = false;
        }
        if(cantidad < 0) {
            System.out.println("La cantidad no puede ser negativa");
            valido = false;
        }
        return valido;
    }
}
