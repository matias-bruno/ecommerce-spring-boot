/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techlab.spring1.service;

import com.techlab.spring1.model.Product;
import com.techlab.spring1.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author matias-bruno
 */

@Service
public class ProductService {
    private ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }
    
    public Product saveProduct(Product newProduct) {
        newProduct.setNombre(formatearNombre(newProduct.getNombre()));
        if(productoValido(newProduct.getNombre(), newProduct.getPrecio(), newProduct.getStock())) {
            Product product = productRepository.save(newProduct);
            System.out.println("Producto ingresado");
            return product;
        } else {
            System.out.println("No se pudo ingresar el producto porque contenia datos no validos");
        }
        return null;
    }

    public Product updateProduct(Long id, Product newProduct) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        String nombreProducto = newProduct.getNombre();
        double precioProducto = newProduct.getPrecio();
        int cantidadEnStock = newProduct.getStock();
        if(productoValido(nombreProducto, precioProducto, cantidadEnStock)) {
            producto.setNombre(nombreProducto);
            producto.setPrecio(precioProducto);
            producto.setStock(cantidadEnStock);
            productRepository.save(producto);
            System.out.println("Producto actualizado");
            return producto;
        } else {
            System.out.println("No se pudo actualizar el producto porque contenia datos no validos");
        }
        return null;
    }
    public Product findProductById(Long id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return producto;
    }
    public void deleteProduct(Long id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(producto);
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
