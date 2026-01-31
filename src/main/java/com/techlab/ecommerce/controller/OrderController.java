package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.service.OrderService;
import com.techlab.ecommerce.dto.OrderRequest;
import com.techlab.ecommerce.dto.OrderResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author matias-bruno
 */

@RestController
public class OrderController {

    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
        
    @PostMapping("/api/orders")
    @PreAuthorize("hasRole('USER')")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest, Authentication authentication) {
        String username = authentication.getName();
        return orderService.createOrder(orderRequest, username);
    }
    
    @GetMapping("/api/orders")
    @PreAuthorize("hasRole('USER')")
    public List<OrderResponse> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        return orderService.getOrdersByUser(username);
    }
    
    @GetMapping("/api/admin/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @GetMapping("/api/orders/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public OrderResponse getOrderById(@PathVariable Long id, Authentication authentication) {
        return orderService.getOrderById(id, authentication);
    }
    
    @GetMapping("/api/admin/orders/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getOrdersByUser(@PathVariable String username) {
        return orderService.getOrdersByUser(username);
    }
}
