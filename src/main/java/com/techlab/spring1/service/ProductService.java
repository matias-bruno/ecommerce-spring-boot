package com.techlab.spring1.service;

import com.techlab.spring1.dto.ProductRequest;
import com.techlab.spring1.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author matias-bruno
 */

public interface ProductService {

    public Page<ProductResponse> getProducts(Pageable pageable, String name);
    
    public ProductResponse saveProduct(ProductRequest newProductRequest);

    public ProductResponse updateProduct(Long id, ProductRequest productRequest);

    public ProductResponse findProductById(Long id);

    public void deleteProduct(Long id);
}
