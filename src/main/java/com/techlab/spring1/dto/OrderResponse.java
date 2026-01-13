package com.techlab.spring1.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class OrderResponse {
    
    private Long id;

    private LocalDateTime orderDate;

    private Double totalAmount;

    private Long userId;

    private List<OrderItemDto> orderItems;
}
