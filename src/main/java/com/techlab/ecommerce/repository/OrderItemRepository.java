package com.techlab.ecommerce.repository;

import com.techlab.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matias-bruno
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
