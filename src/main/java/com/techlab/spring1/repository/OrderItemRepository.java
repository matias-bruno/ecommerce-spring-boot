package com.techlab.spring1.repository;

import com.techlab.spring1.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matias-bruno
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
