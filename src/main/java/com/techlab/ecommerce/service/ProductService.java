package com.techlab.ecommerce.service;

import com.techlab.ecommerce.dto.ProductRequest;
import com.techlab.ecommerce.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author matias-bruno
 */

public interface ProductService {

    public Page<ProductResponse> getProducts(
            String name,
            String categorySlug,
            Double minPrice,
            Double maxPrice,
            Pageable pageable);
    
    public ProductResponse saveProduct(ProductRequest newProductRequest);

    public ProductResponse updateProduct(Long id, ProductRequest productRequest);

    public ProductResponse findProductById(Long id);

    public void deleteProduct(Long id);
}
