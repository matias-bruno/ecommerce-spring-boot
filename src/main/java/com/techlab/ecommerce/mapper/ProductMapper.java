package com.techlab.ecommerce.mapper;

import com.techlab.ecommerce.dto.ProductRequest;
import com.techlab.ecommerce.dto.ProductResponse;
import com.techlab.ecommerce.model.Category;
import com.techlab.ecommerce.model.Product;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author matias-bruno
 */
public class ProductMapper {
    
    public static ProductResponse toDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        productResponse.setCategoryName(product.getCategory().getName());
        productResponse.setCategorySlug(product.getCategory().getSlug());
        return productResponse;
    }
    
    public static Product toEntity(ProductRequest productRequest, Category category) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        product.setCategory(category);
        return product;
    }
}
