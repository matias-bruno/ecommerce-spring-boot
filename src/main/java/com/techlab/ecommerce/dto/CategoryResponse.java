package com.techlab.ecommerce.dto;

import lombok.Data;

/**
 *
 * @author matias-bruno
 */
@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
}

