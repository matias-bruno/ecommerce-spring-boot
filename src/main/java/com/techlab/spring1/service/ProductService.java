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

    public List<Product> findAllProducts(String nombre, Double precio) {
        if (!nombre.isEmpty() && precio > 0) {
            return this.productRepository.findByNombreContainingIgnoreCaseAndPrecioLessThanEqual(nombre, precio);
        }

        if (!nombre.isEmpty()) {
            return this.productRepository.findByNombreContainingIgnoreCase(nombre);
        }

        if (precio > 0) {
            return this.productRepository.findByPrecioLessThanEqual(precio);
        }

        return this.productRepository.findAll();
    }

    public Product saveProduct(Product newProduct) {
        String nombreConFormato = ProductService.formatearNombre(newProduct.getNombre());
        newProduct.setNombre(nombreConFormato);
        if(productRepository.existsByNombre(nombreConFormato)) {
            throw new DuplicateResourceException("Producto con nombre '" + nombreConFormato + "' ya existe" );
        }
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, Product newProduct) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id " + id));
        
        String nombreConFormato = ProductService.formatearNombre(newProduct.getNombre());
        newProduct.setNombre(nombreConFormato);
        
        if (!newProduct.getNombre().equalsIgnoreCase(producto.getNombre())) {
            if(productRepository.existsByNombre(nombreConFormato)) {
                throw new DuplicateResourceException("Producto con nombre '" + nombreConFormato + "' ya existe" );
            }
        }
        
        producto.setNombre(newProduct.getNombre());
        producto.setPrecio(newProduct.getPrecio());
        producto.setStock(newProduct.getStock());
        producto.setDescripcion(newProduct.getDescripcion());
        producto.setImagenUrl(newProduct.getImagenUrl());

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
