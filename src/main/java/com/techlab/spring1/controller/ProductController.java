/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techlab.spring1.controller;

import com.techlab.spring1.model.Product;
import com.techlab.spring1.service.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author matias-bruno
 */
@RestController
@RequestMapping("/api/productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /products?nombre="product"&precio=123
    @GetMapping
    public List<Product> listProducts(@RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(required = false, defaultValue = "0") Double precio) {
        return productService.findAllProducts(nombre, precio);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product newProduct) {
        return productService.updateProduct(id, newProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
