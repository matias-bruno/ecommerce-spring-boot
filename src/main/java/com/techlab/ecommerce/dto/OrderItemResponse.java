package com.techlab.ecommerce.dto;

import lombok.Data;

/**
 *
 * @author matias-bruno
 */
@Data
public class OrderItemResponse {
   private Long productId;
   private Integer quantity;
   private String productName; 
   private Double priceAtPurchase;
   private Double subTotal;
}
