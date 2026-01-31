package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.dto.ProductRequest;
import com.techlab.ecommerce.dto.ProductResponse;
import com.techlab.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author matias-bruno
 */
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Le pasamos un PageableDefault con los valores que usaremos por defecto
    // Una url personalizada sería "/api/products?page=1&size=20&sort=price,asc
    @GetMapping("/api/products")
    public Page<ProductResponse> getProducts(@PageableDefault(page = 0, size = 20, sort = "name") Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String name) {
        return productService.getProducts(pageable, name);
    }

    @GetMapping("/api/products/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }
    
    @PostMapping("/api/admin/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse newProductResponse = productService.saveProduct(productRequest);
        return new ResponseEntity<>(newProductResponse, HttpStatus.CREATED); // 201
    }
    
    @PutMapping("/api/admin/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/api/admin/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
