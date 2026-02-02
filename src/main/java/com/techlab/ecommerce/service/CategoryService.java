package com.techlab.ecommerce.service;

import com.techlab.ecommerce.dto.CategoryRequest;
import com.techlab.ecommerce.dto.CategoryResponse;
import java.util.List;

/**
 *
 * @author matias-bruno
 */

public interface CategoryService {

    public List<CategoryResponse> findAllCategories();
    
    public CategoryResponse findCategoryBySlug(String slug);
    
    public CategoryResponse createCategory(CategoryRequest categoryRequest);
    
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, String slug);
    
    public void deleteCategory(String slug);
}
