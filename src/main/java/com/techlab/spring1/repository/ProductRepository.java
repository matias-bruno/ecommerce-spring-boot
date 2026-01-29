package com.techlab.spring1.repository;

import com.techlab.spring1.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matias-bruno
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String name);
    Page<Product> findByNameContainingIgnoreCase(Pageable pageable, String name);
}
