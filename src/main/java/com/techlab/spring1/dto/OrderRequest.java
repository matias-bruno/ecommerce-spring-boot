package com.techlab.spring1.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class OrderRequest {
    @NotEmpty
    @Valid // Para validar cada item individualmente
    private List<OrderItemRequest> orderItems;
}
