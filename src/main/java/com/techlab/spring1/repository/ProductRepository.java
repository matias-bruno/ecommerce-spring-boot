/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    List<Product> findByNombreContaining(String nombre);
    List<Product> findByPrecioLessThanEqual(Double precio);
    List<Product> findByNombreContainingAndPrecioLessThanEqual(String nombre, Double precio);
    
}
