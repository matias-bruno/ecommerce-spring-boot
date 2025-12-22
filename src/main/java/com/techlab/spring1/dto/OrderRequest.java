package com.techlab.spring1.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class OrderRequest {
    private List<OrderItemRequest> items;
}
