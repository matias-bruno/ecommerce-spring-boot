package com.techlab.spring1.repository;

import com.techlab.spring1.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matias-bruno
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceLessThanEqual(Double price);
    List<Product> findByNameContainingIgnoreCaseAndPriceLessThanEqual(String name, Double price);
    
}
