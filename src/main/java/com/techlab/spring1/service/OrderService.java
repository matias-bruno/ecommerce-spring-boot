package com.techlab.spring1.service;

import com.techlab.spring1.exception.ResourceNotFoundException;
import com.techlab.spring1.model.Order;
import com.techlab.spring1.model.OrderItem;
import com.techlab.spring1.model.Product;
import com.techlab.spring1.repository.ProductRepository;
import com.techlab.spring1.dto.OrderItemRequest;
import com.techlab.spring1.dto.OrderRequest;
import com.techlab.spring1.exception.InsufficientStockException;
import com.techlab.spring1.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author matias-bruno
 */
@Service
public class OrderService {
    
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    
    
    public OrderService(ProductRepository productRepository,
            OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderRequest orderRequest) {
        
        List<OrderItem> orderItems = new ArrayList<>();
        Double total = 0.0;
        Order order = new Order();
        
        // Iterar sobre los ítems recibidos del frontend
        for(OrderItemRequest orderItemRequest : orderRequest.getItems()) {
            OrderItem orderItem = new OrderItem();
            // Obtener el producto
            Long productId = orderItemRequest.getProductId();
            Optional<Product> productOpt = productRepository.findById(productId);
            if(productOpt.isEmpty()) {
                throw new ResourceNotFoundException("No se encontró el producto con id " + productId);
            }
            Product product = productOpt.get();
            
            // Validación de stock
            int requestedQuantity = orderItemRequest.getQuantity();
            int availableQuantity = product.getStock();
            if(availableQuantity < requestedQuantity) {
                throw new InsufficientStockException(String.format(
                        "Stock insuficiente para producto '%s'. Requerido: %d, Disponible: %d", 
                        product.getName(), requestedQuantity, availableQuantity));
            }
            
            // Reducción del stock
            product.setStock(availableQuantity - requestedQuantity);
            
            // Se guarda información en el orderItem
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setProductName(product.getName());
            
            orderItems.add(orderItem);
            
            // Se suma al total del pedido
            total += product.getPrice() * orderItemRequest.getQuantity();
        }
        
        // Actualizamos los datos del pedido
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setOrderItems(orderItems);
        
        // Guardamos y devolvemos el nuevo pedido
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
