package com.techlab.ecommerce.repository;

import com.techlab.ecommerce.model.Order;
import com.techlab.ecommerce.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matias-bruno
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}