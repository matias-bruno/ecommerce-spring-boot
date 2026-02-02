package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.dto.CategoryRequest;
import com.techlab.ecommerce.dto.CategoryResponse;
import com.techlab.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author matias-bruno
 */

@RestController
public class CategoryController {
    private final CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping("/api/categories")
    public List<CategoryResponse> findAllCategories() {
        return categoryService.findAllCategories();
    }
    
    @GetMapping("/api/categories/{slug}")
    public CategoryResponse findCategoryBySlug(@PathVariable String slug) {
        return categoryService.findCategoryBySlug(slug);
    }
    
    @PostMapping("/api/admin/categories")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }
    
    @PutMapping("/api/admin/categories/{slug}")
    public CategoryResponse updateCategory(@Valid @RequestBody CategoryRequest categoryRequest,
            @PathVariable String slug) {
        return categoryService.updateCategory(categoryRequest, slug);
    }
    
    @DeleteMapping("/api/admin/categories/{slug}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String slug) {
        categoryService.deleteCategory(slug);
        return ResponseEntity.noContent().build();
    }
    
}
