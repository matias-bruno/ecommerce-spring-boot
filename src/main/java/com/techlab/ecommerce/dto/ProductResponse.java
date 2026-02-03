package com.techlab.ecommerce.dto;

import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class ProductResponse {
    
    private Long id;
    
    private String name;

    private Double  price;

    private Integer stock;
    
    private String description;

    private String imageUrl;
    
    private String categorySlug;
    
    private String categoryName;
}
