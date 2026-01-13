package com.techlab.spring1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class OrderItemDto {
   @NotNull(message = "El id de producto es obligatorio")
   private Long productId;
   @Min(value = 1, message = "La cantidad debe ser al menos 1")
   private Integer quantity;
}
