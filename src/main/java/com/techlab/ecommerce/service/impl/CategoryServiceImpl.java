package com.techlab.ecommerce.service.impl;

import com.techlab.ecommerce.dto.CategoryRequest;
import com.techlab.ecommerce.dto.CategoryResponse;
import com.techlab.ecommerce.exception.DuplicateResourceException;
import com.techlab.ecommerce.exception.ResourceNotFoundException;
import com.techlab.ecommerce.mapper.CategoryMapper;
import com.techlab.ecommerce.model.Category;
import com.techlab.ecommerce.repository.CategoryRepository;
import com.techlab.ecommerce.service.CategoryService;
import com.techlab.ecommerce.util.TextUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author matias-bruno
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public CategoryResponse findCategoryBySlug(String slug) {
        Category category = this.categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria " + slug + " no encontrada."));
        return CategoryMapper.toDto(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        String name = TextUtils.toTitleCase(categoryRequest.getName());
        String slug = TextUtils.slugify(name);
        
        if (this.categoryRepository.findBySlug(slug).isPresent()) {
            throw new DuplicateResourceException("Categoria " + slug + " ya exite.");
        }
        
        Category category = new Category();
        category.setName(name);
        category.setSlug(slug);
        
        category = categoryRepository.save(category);
        
        return CategoryMapper.toDto(category);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la categoria con id " + id));
        
        String updatedName = TextUtils.toTitleCase(categoryRequest.getName());
        String updatedSlug = TextUtils.slugify(updatedName);
        String slug = category.getSlug();
        
        if(!slug.equals(updatedSlug) && this.categoryRepository.findBySlug(updatedSlug).isPresent()) {
            throw new DuplicateResourceException("Categoria " + slug + " ya existe.");
        }
        
        category.setName(updatedName);
        category.setSlug(updatedSlug);
        
        categoryRepository.save(category);
        
        return CategoryMapper.toDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la categoria con id " + id));
        categoryRepository.delete(category);
    }

}
