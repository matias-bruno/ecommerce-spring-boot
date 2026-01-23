package com.techlab.spring1.service;

import com.techlab.spring1.dto.ProductRequest;
import com.techlab.spring1.dto.ProductResponse;
import com.techlab.spring1.exception.DuplicateResourceException;
import com.techlab.spring1.exception.ResourceNotFoundException;
import com.techlab.spring1.mapper.ProductMapper;
import com.techlab.spring1.model.Product;
import com.techlab.spring1.repository.ProductRepository;
import com.techlab.spring1.util.TextUtils;
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
        String nombreConFormato = TextUtils.toTitleCase(newProductRequest.getName());
        newProductRequest.setName(nombreConFormato);
        if(productRepository.existsByName(nombreConFormato)) {
            throw new DuplicateResourceException("Producto con nombre '" + nombreConFormato + "' ya existe" );
        }
        Product newProduct = ProductMapper.toEntity(newProductRequest);
        productRepository.save(newProduct);
        return ProductMapper.toDto(newProduct);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id " + id));
        
        // Queremos convertir la cadena de nombre a un formato pre-establecido
        String nombreConFormato = TextUtils.toTitleCase(productRequest.getName());
        productRequest.setName(nombreConFormato);
        
        // Si se quiere cambiar el nombre porque no es el mismo que ya tenía
        if (!productRequest.getName().equalsIgnoreCase(product.getName())) {
            // El nuevo nombre tiene que ser único
            if(productRepository.existsByName(nombreConFormato)) {
                throw new DuplicateResourceException("Producto con nombre '" + nombreConFormato + "' ya existe" );
            }
        }
        
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        
        productRepository.save(product);

        return ProductMapper.toDto(product);
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
}
