package com.techlab.ecommerce.service;

import com.techlab.ecommerce.dto.OrderRequest;
import com.techlab.ecommerce.dto.OrderResponse;
import java.util.List;
import org.springframework.security.core.Authentication;

/**
 *
 * @author matias-bruno
 */

public interface OrderService {


    public OrderResponse createOrder(OrderRequest orderRequest, String username);

    public List<OrderResponse> getAllOrders();

    public OrderResponse getOrderById(Long id, Authentication authentication);

    public List<OrderResponse> getOrdersByUser(String username);

}
