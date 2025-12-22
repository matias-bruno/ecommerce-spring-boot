package com.techlab.spring1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Entity
@Data
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    // Referencia al pedido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore // IGNORAR ESTA REFERENCIA AL SERIALIZAR OrderItem
    private Order order;
    
    private Integer quantity;
    
    // Datos del producto al momento de la compra (snapshot)
    private String productName; 
    private Double priceAtPurchase; 
    
    // TODO: poder descomentar esto y que funcione
    // Referencia opcional al producto original (para referencia)
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "product_id")
    //private Product product; 
}
