package com.techlab.spring1.dto;

import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class OrderItemRequest {
   private Long productId;
   private Integer quantity;
}
