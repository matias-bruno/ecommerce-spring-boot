package com.techlab.spring1.repository;

import com.techlab.spring1.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matias-bruno
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
