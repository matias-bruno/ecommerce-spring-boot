package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.dto.ProductResponse;
import com.techlab.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author matias-bruno
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Le pasamos un PageableDefault con los valores que usaremos por defecto
    // Una url personalizada sería "/api/products?page=1&size=20&sort=price,asc
    @GetMapping
    public Page<ProductResponse> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minprice,
            @RequestParam(required = false) Double maxprice,
            @PageableDefault(page = 0, size = 20, sort = "name") Pageable pageable) {
        return productService.getProducts(name, category, minprice, maxprice, pageable, true);
    }
    
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }
}
