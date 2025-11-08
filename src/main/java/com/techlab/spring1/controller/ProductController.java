/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techlab.spring1.controller;

import com.techlab.spring1.model.Product;
import com.techlab.spring1.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author matias-bruno
 */

@RestController
@RequestMapping("/api/productos")
public class ProductController {
    private ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public List<Product> listProducts() {
        return productService.getProducts();
    }
    
    @PostMapping
    public void createProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }
    
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }
    
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody Product newProduct) {
        Product product = productService.getProductById(id);
        if(product != null)
            productService.updateProduct(product,newProduct);
        else
            System.out.println("No se encontro el id");
    }
    
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if(product != null)
            productService.deleteProduct(product);
        else
            System.out.println("No se encontro el id");
    }
}
