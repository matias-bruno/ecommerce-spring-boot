package com.techlab.ecommerce.mapper;

import com.techlab.ecommerce.dto.CategoryResponse;
import com.techlab.ecommerce.model.Category;

/**
 *
 * @author matias-bruno
 */
public class CategoryMapper {
    public static CategoryResponse toDto(Category category) {
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        return dto;
    }
}
