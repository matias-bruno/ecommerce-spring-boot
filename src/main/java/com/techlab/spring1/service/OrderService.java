package com.techlab.spring1.service;

import com.techlab.spring1.exception.ResourceNotFoundException;
import com.techlab.spring1.model.Order;
import com.techlab.spring1.model.OrderItem;
import com.techlab.spring1.model.Product;
import com.techlab.spring1.repository.ProductRepository;
import com.techlab.spring1.dto.OrderItemDto;
import com.techlab.spring1.dto.OrderRequest;
import com.techlab.spring1.dto.OrderResponse;
import com.techlab.spring1.exception.InsufficientStockException;
import com.techlab.spring1.mapper.OrderMapper;
import com.techlab.spring1.model.User;
import com.techlab.spring1.repository.OrderRepository;
import com.techlab.spring1.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author matias-bruno
 */
@Service
public class OrderService {
    
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    
    
    public OrderService(ProductRepository productRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, String username) {
        
        List<OrderItem> orderItems = new ArrayList<>();
        Double total = 0.0;
        Order order = new Order();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Iterar sobre los ítems recibidos del frontend
        for(OrderItemDto orderItemRequest : orderRequest.getOrderItems()) {
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
            productRepository.save(product);
            
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
        order.setUser(user);
        
        // Guardamos el nuevo pedido
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDto(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No se encontró el pedido con id " + id));
        return OrderMapper.toDto(order);
    }

}
