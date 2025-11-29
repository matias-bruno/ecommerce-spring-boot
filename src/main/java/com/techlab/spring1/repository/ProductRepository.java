package com.techlab.spring1.repository;

import com.techlab.spring1.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matias-bruno
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByNombre(String nombre);
    List<Product> findByNombreContainingIgnoreCase(String nombre);
    List<Product> findByPrecioLessThanEqual(Double precio);
    List<Product> findByNombreContainingIgnoreCaseAndPrecioLessThanEqual(String nombre, Double precio);
    
}
