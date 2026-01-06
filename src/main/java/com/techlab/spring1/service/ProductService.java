package com.techlab.spring1.service;

import com.techlab.spring1.dto.ProductRequest;
import com.techlab.spring1.dto.ProductResponse;
import com.techlab.spring1.exception.DuplicateResourceException;
import com.techlab.spring1.exception.ResourceNotFoundException;
import com.techlab.spring1.mapper.ProductMapper;
import com.techlab.spring1.model.Product;
import com.techlab.spring1.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<ProductResponse> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductResponse saveProduct(ProductRequest newProductRequest) {
        String nombreConFormato = ProductService.formatearNombre(newProductRequest.getName());
        newProductRequest.setName(nombreConFormato);
        if(productRepository.existsByName(nombreConFormato)) {
            throw new DuplicateResourceException("Producto con nombre '" + nombreConFormato + "' ya existe" );
        }
        Product newProduct = ProductMapper.toEntity(newProductRequest);
        productRepository.save(newProduct);
        return ProductMapper.toDto(newProduct);
    }

    public ProductResponse updateProduct(Long id, ProductRequest newProductRequest) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id " + id));
        
        // Queremos convertir la cadena de nombre a un formato pre-establecido
        String nombreConFormato = ProductService.formatearNombre(newProductRequest.getName());
        newProductRequest.setName(nombreConFormato);
        
        // Si se quiere cambiar el nombre porque no es el mismo que ya tenía
        if (!newProductRequest.getName().equalsIgnoreCase(productToUpdate.getName())) {
            // El nuevo nombre tiene que ser único
            if(productRepository.existsByName(nombreConFormato)) {
                throw new DuplicateResourceException("Producto con nombre '" + nombreConFormato + "' ya existe" );
            }
        }
        
        // Lo hacemos más fácil, actualizamos todos  los atributos
        // También se podrían actualizar solo los que esten modificados
        productToUpdate.setName(newProductRequest.getName());
        productToUpdate.setPrice(newProductRequest.getPrice());
        productToUpdate.setStock(newProductRequest.getStock());
        productToUpdate.setDescription(newProductRequest.getDescription());
        productToUpdate.setImageUrl(newProductRequest.getImageUrl());
        
        Product updatedProduct = productRepository.save(productToUpdate);

        return ProductMapper.toDto(updatedProduct);
    }

    public ProductResponse findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id " + id));
        return ProductMapper.toDto(product);
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
