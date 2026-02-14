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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author matias-bruno
 */

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    
    private final ProductService productService;
    
    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/inactive")
    public Page<ProductResponse> getInactive(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minprice,
            @RequestParam(required = false) Double maxprice,
            @PageableDefault(page = 0, size = 20, sort = "name") Pageable pageable) {
        return productService.getProducts(name, category, minprice, maxprice, pageable, false);
    }
    
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse newProductResponse = productService.saveProduct(productRequest);
        return new ResponseEntity<>(newProductResponse, HttpStatus.CREATED); // 201
    }
    
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build(); // 204
    }
    
    @PutMapping("/{id}/restore")
    public ResponseEntity<Void> restoreProduct(@PathVariable Long id) {
        productService.restoreProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDeleteProduct(@PathVariable Long id) {
        productService.hardDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
