package com.techlab.spring1.service;

import com.techlab.spring1.exception.DuplicateResourceException;
import com.techlab.spring1.exception.ResourceNotFoundException;
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

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts(String name, Double price) {
        if (!name.isEmpty() && price > 0) {
            return this.productRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(name, price);
        }

        if (!name.isEmpty()) {
            return this.productRepository.findByNameContainingIgnoreCase(name);
        }

        if (price > 0) {
            return this.productRepository.findByPriceLessThanEqual(price);
        }

        return this.productRepository.findAll();
    }

    public Product saveProduct(Product newProduct) {
        String nombreConFormato = ProductService.formatearNombre(newProduct.getName());
        newProduct.setName(nombreConFormato);
        if(productRepository.existsByName(nombreConFormato)) {
            throw new DuplicateResourceException("Producto con nombre '" + nombreConFormato + "' ya existe" );
        }
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, Product newProduct) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id " + id));
        
        String nombreConFormato = ProductService.formatearNombre(newProduct.getName());
        newProduct.setName(nombreConFormato);
        
        if (!newProduct.getName().equalsIgnoreCase(producto.getName())) {
            if(productRepository.existsByName(nombreConFormato)) {
                throw new DuplicateResourceException("Producto con nombre '" + nombreConFormato + "' ya existe" );
            }
        }
        
        producto.setName(newProduct.getName());
        producto.setPrice(newProduct.getPrice());
        producto.setStock(newProduct.getStock());
        producto.setDescription(newProduct.getDescription());
        producto.setImageUrl(newProduct.getImageUrl());

        productRepository.save(producto);
        return producto;
    }

    public Product findProductById(Long id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id " + id));
        return producto;
    }

    public void deleteProduct(Long id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id " + id));
        productRepository.delete(producto);
    }

    // Metodo auxiliar para nombres de productos
    private static String formatearNombre(String nombre) {
        String[] palabras = nombre.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String palabra : palabras) {
            sb.append(palabra.substring(0, 1).toUpperCase());
            sb.append(palabra.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
