package com.techlab.ecommerce.mapper;

import com.techlab.ecommerce.dto.OrderItemResponse;
import com.techlab.ecommerce.dto.OrderResponse;
import com.techlab.ecommerce.model.Order;
import com.techlab.ecommerce.model.OrderItem;
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
        
        List<OrderItemResponse> orderItemDtos = new ArrayList<>();
        
        for(OrderItem item : order.getOrderItems()) {
            OrderItemResponse itemDto = new OrderItemResponse();
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setProductName(item.getProductName());
            itemDto.setPriceAtPurchase(item.getPriceAtPurchase());
            itemDto.setSubTotal(item.getQuantity() * item.getPriceAtPurchase());
            orderItemDtos.add(itemDto);
        }
        
        orderResponse.setOrderItems(orderItemDtos);
        
        return orderResponse;
    }
}
