package com.techlab.spring1.mapper;

import com.techlab.spring1.dto.OrderItemDto;
import com.techlab.spring1.dto.OrderRequest;
import com.techlab.spring1.dto.OrderResponse;
import com.techlab.spring1.model.Order;
import com.techlab.spring1.model.OrderItem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matias-bruno
 */
public class OrderMapper {
    public static OrderResponse toDto(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        
        orderResponse.setId(order.getId());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setUserId(order.getUser().getId());
        
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        
        for(OrderItem item : order.getOrderItems()) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setQuantity(item.getQuantity());
            orderItemDtos.add(itemDto);
        }
        
        orderResponse.setOrderItems(orderItemDtos);
        
        return orderResponse;
    }
    
    public static Order toEntity(OrderRequest orderRequest) {
        return null;
    }
}
