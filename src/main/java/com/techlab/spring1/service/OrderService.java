package com.techlab.spring1.service;

import com.techlab.spring1.exception.ResourceNotFoundException;
import com.techlab.spring1.model.Order;
import com.techlab.spring1.model.OrderItem;
import com.techlab.spring1.model.Product;
import com.techlab.spring1.repository.ProductRepository;
import com.techlab.spring1.dto.OrderItemRequest;
import com.techlab.spring1.dto.OrderRequest;
import com.techlab.spring1.dto.OrderResponse;
import com.techlab.spring1.exception.InsufficientStockException;
import com.techlab.spring1.mapper.OrderMapper;
import com.techlab.spring1.model.User;
import com.techlab.spring1.repository.OrderRepository;
import com.techlab.spring1.repository.UserRepository;
import com.techlab.spring1.util.CollectionUtils;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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
        
        //Validamos que el usuario existe
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        List<OrderItem> orderItems = new ArrayList<>();
        Double total = 0.0;
        Order order = new Order();
                
        List<Long> productIds = orderRequest.getOrderItems().stream()
                .map(item -> item.getProductId())
                .toList();
        
        // Obtenemos todos los productos con una sola query
        List<Product> products = productRepository.findAllById(productIds);
        
        Map<Long,Product> productMap = CollectionUtils.listToMap(products);
        
        
        // Iterar sobre los ítems recibidos del frontend
        for(OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            // Obtener el producto
            Long productId = orderItemRequest.getProductId();
            
            // Buscando en mapa hacemos más rápido
            Product product = productMap.get(productId);
            if(product == null) {
                throw new ResourceNotFoundException("No se encontró el producto con id " + productId);
            }
            
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
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setProductName(product.getName());
            
            orderItems.add(orderItem);
            
            // Se suma al total del pedido
            total += product.getPrice() * orderItemRequest.getQuantity();
        }
        
        // Completamos el pedido
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setOrderItems(orderItems);
        order.setUser(user);
        orderRepository.save(order);
        
        // Actualizamos los productos con el nuevo stock
        productRepository.saveAll(products);
        
        return OrderMapper.toDto(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id, Authentication authentication) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No se encontró el pedido con id " + id));
        
        String currentUsername = authentication.getName();
        
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        
        if(!isAdmin && !order.getUser().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("No tienes permiso para ver este pedido.");
        }
        return OrderMapper.toDto(order);
    }

    public List<OrderResponse> getOrdersByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return orderRepository.findByUser(user)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

}
