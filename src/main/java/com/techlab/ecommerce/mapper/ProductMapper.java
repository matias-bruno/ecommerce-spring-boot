package com.techlab.ecommerce.mapper;

import com.techlab.ecommerce.dto.ProductRequest;
import com.techlab.ecommerce.dto.ProductResponse;
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
        return productResponse;
    }
    
    public static Product toEntity(ProductRequest productRequest) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        return product;
    }
}
